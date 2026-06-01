import {Component, inject, input} from '@angular/core';
import {Film} from '../../dtos/programm';
import {DurationPipe} from '../../services/filmlaufzeit.pipe';
import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-programmeintrag',
  imports: [
    DurationPipe,
    DatePipe
],
  templateUrl: './programmeintrag.component.html',
  styleUrl: './programmeintrag.component.css',
})
export class ProgrammeintragComponent {
  private router = inject(Router);

  public readonly film = input.required<Film>();

  waehleVorstellung(vorstellungId: string) {
    this.router.navigate(['/kartenverkauf', vorstellungId]);
  }
}
