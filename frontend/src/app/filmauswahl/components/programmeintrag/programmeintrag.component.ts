import {Component, Input} from '@angular/core';
import {Film} from '../../dtos/programm';
import {DurationPipe} from '../../services/filmlaufzeit.pipe';
import { DatePipe } from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-programmeintrag',
  imports: [
    DurationPipe,
    DatePipe
],
  templateUrl: './programmeintrag.component.html',
  styleUrl: './programmeintrag.component.css',
  standalone: true,
})
export class ProgrammeintragComponent {

  @Input({required: true})
  public film!: Film;

  constructor(private router: Router) {
  }

  waehleVorstellung(vorstellungId: string) {
    this.router.navigate(['/kartenverkauf', vorstellungId]);
  }
}
