import {Component, EventEmitter, Input, Output} from '@angular/core';
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
  standalone: true,
})
export class PlatzanzahlComponent {

  @Input() vorstellung!: Vorstellung;

  @Output()
  onPlatzanzahlBestaetigt: EventEmitter<number> = new EventEmitter();

  @Output()
  onReset: EventEmitter<void> = new EventEmitter();

  maxAnzahl: number = 10 // TODO get from backend for vorstellung
  anzahlOptions: number[] = Array.from({length: this.maxAnzahl}, (_, i) => i + 1);

  platzanzahlControl: FormControl<number | null> = new FormControl<number | null>(null,
    [Validators.required, Validators.min(1), Validators.max(this.maxAnzahl)]);

  fertig: boolean = false;

  uebermittlePlatzanzahl() {
    if (!isPresent(this.platzanzahlControl.value)) {
      return;
    }
    this.fertig = true;
    this.onPlatzanzahlBestaetigt.emit(this.platzanzahlControl.value);
  }

}
