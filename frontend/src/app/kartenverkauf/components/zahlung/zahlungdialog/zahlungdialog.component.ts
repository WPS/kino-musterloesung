import {Component, ElementRef, EventEmitter, Output, ViewChild} from '@angular/core';
import {GeldbetragPipe} from '../../../services/geldbetrag.pipe';
import {Zahlungsvorgang} from '../../../dtos/kartenverkauf';

@Component({
  selector: 'app-zahlungdialog',
  imports: [
    GeldbetragPipe
  ],
  templateUrl: './zahlungdialog.component.html',
  styleUrl: './zahlungdialog.component.css'
})
export class ZahlungdialogComponent {

  zahlungvorgang: Zahlungsvorgang | undefined;

  @Output()
  onDialogGeschlossen = new EventEmitter<Zahlungsvorgang>();

  @ViewChild('zahlungDialogModal')
  dialogRef!: ElementRef<HTMLDialogElement>;

  oeffneDialog(zahlungsvorgang: Zahlungsvorgang): void {
    this.zahlungvorgang = zahlungsvorgang;
    this.dialogRef.nativeElement.showModal();
  }

  schliesseDialog(): void {
    this.dialogRef.nativeElement.close();
    this.onDialogGeschlossen.emit(this.zahlungvorgang);
  }

}
