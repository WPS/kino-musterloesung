import {Component, ElementRef, output, signal, viewChild} from '@angular/core';
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

  readonly zahlungvorgang = signal<Zahlungsvorgang | undefined>(undefined);

  readonly onDialogGeschlossen = output<Zahlungsvorgang>();

  readonly dialogRef = viewChild.required<ElementRef<HTMLDialogElement>>('zahlungDialogModal');

  oeffneDialog(zahlungsvorgang: Zahlungsvorgang): void {
    this.zahlungvorgang.set(zahlungsvorgang);
    this.dialogRef().nativeElement.showModal();
  }

  schliesseDialog(): void {
    this.dialogRef().nativeElement.close();
    this.onDialogGeschlossen.emit(this.zahlungvorgang()!);
  }

}
