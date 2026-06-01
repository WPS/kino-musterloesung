import {Component, DestroyRef, inject, OnInit, input, output, signal, viewChild} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
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
  private kartenverkaufService = inject(KartenverkaufService);
  private destroyRef = inject(DestroyRef);

  readonly vorstellung = input.required<Vorstellung>();

  readonly plaetze = input.required<ZusammenhaengendePlaetze>();

  readonly onZahlungBestaetigt = output<Zahlungsvorgang>();

  readonly gesamtbetrag = signal<Geldbetrag | undefined>(undefined);

  readonly zahlungsvorgang = signal<Zahlungsvorgang | undefined>(undefined);

  readonly fertig = signal(false);

  readonly zahlungDialog = viewChild.required<ZahlungdialogComponent>('zahlungDialog');

  ngOnInit(): void {
    this.kartenverkaufService.ermittlePreis(this.vorstellung().uuid, this.plaetze())
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((gesamtbetrag: Geldbetrag) => this.gesamtbetrag.set(gesamtbetrag));
  }

  oeffneZahlungDialog() {
    this.kartenverkaufService.starteZahlungsvorgang(this.vorstellung().uuid, this.plaetze())
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((zahlungsvorgang: Zahlungsvorgang) => {
        this.zahlungsvorgang.set(zahlungsvorgang);
        this.zahlungDialog().oeffneDialog(zahlungsvorgang);
      });
  }

  zahlungDialogGeschlossen(zahlungsvorgang: Zahlungsvorgang) {
    this.kartenverkaufService.bestaetigeZahlung(this.zahlungsvorgang()!.auftragsnummer)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((zahlungsstatus: Zahlungsstatus) => {
        if (zahlungsstatus.status == "Eingegangen") {
          this.onZahlungBestaetigt.emit(zahlungsvorgang);
          this.fertig.set(true);
        }
      });
  }

}
