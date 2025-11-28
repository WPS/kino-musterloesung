import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {
  Geldbetrag,
  Vorstellung,
  Zahlungsstatus,
  Zahlungsvorgang,
  ZusammenhaengendePlaetze
} from '../../dtos/kartenverkauf';
import {GeldbetragPipe} from '../../services/geldbetrag.pipe';
import {ZahlungdialogComponent} from './zahlungdialog/zahlungdialog.component';
import {KartenverkaufService} from '../../services/kartenverkauf.service';

@Component({
  selector: 'app-zahlung',
  imports: [
    GeldbetragPipe,
    ZahlungdialogComponent
  ],
  templateUrl: './zahlung.component.html',
  styleUrl: './zahlung.component.css'
})
export class ZahlungComponent implements OnInit {

  @Input({required: true})
  vorstellung!: Vorstellung;

  @Input({required: true})
  plaetze!: ZusammenhaengendePlaetze;

  @Output()
  onZahlungBestaetigt: EventEmitter<Zahlungsvorgang> = new EventEmitter();

  gesamtbetrag: Geldbetrag | undefined;

  zahlungsvorgang: Zahlungsvorgang | undefined;

  fertig: boolean = false;

  @ViewChild('zahlungDialog')
  zahlungDialog!: ZahlungdialogComponent;

  constructor(private kartenverkaufService: KartenverkaufService) {
  }

  ngOnInit(): void {
    this.kartenverkaufService.ermittlePreis(this.vorstellung.uuid, this.plaetze).subscribe((gesamtbetrag: Geldbetrag) => {
      this.gesamtbetrag = gesamtbetrag
    });
  }

  oeffneZahlungDialog() {
    this.kartenverkaufService.starteZahlungsvorgang(this.vorstellung.uuid, this.plaetze).subscribe((zahlungsvorgang: Zahlungsvorgang) => {
      this.zahlungsvorgang = zahlungsvorgang;
      this.zahlungDialog.oeffneDialog(zahlungsvorgang)
    });
  }

  zahlungDialogGeschlossen(zahlungsvorgang: Zahlungsvorgang) {
    this.kartenverkaufService.bestaetigeZahlung(this.zahlungsvorgang!.auftragsnummer).subscribe((zahlungsstatus: Zahlungsstatus) => {
      if (zahlungsstatus.status == "Eingegangen") {
        this.onZahlungBestaetigt.emit(zahlungsvorgang);
        this.fertig = true;
      }
    })
  }

}
