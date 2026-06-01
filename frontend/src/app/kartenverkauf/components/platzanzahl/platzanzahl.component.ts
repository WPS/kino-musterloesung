import {Component, input, output, signal} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import {isPresent} from '../../../common/utils';

import {Vorstellung} from '../../dtos/kartenverkauf';

@Component({
  selector: 'app-platzanzahl',
  imports: [
    ReactiveFormsModule
],
  templateUrl: './platzanzahl.component.html',
  styleUrl: './platzanzahl.component.css',
})
export class PlatzanzahlComponent {

  readonly vorstellung = input.required<Vorstellung>();

  readonly onPlatzanzahlBestaetigt = output<number>();

  readonly onReset = output<void>();

  maxAnzahl: number = 10 // TODO get from backend for vorstellung
  anzahlOptions: number[] = Array.from({length: this.maxAnzahl}, (_, i) => i + 1);

  platzanzahlControl: FormControl<number | null> = new FormControl<number | null>(null,
    [Validators.required, Validators.min(1), Validators.max(this.maxAnzahl)]);

  readonly fertig = signal(false);

  uebermittlePlatzanzahl() {
    if (!isPresent(this.platzanzahlControl.value)) {
      return;
    }
    this.fertig.set(true);
    this.onPlatzanzahlBestaetigt.emit(Number(this.platzanzahlControl.value));
  }

}
