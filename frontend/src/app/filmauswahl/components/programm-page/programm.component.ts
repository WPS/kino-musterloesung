import {Component} from '@angular/core';
import {ProgrammService} from '../../services/programm.service';
import {Film} from '../../dtos/programm';
import {KalenderComponent} from '../kalender/kalender.component';
import {ProgrammeintragComponent} from '../programmeintrag/programmeintrag.component';

import {format} from 'date-fns';
import {NavbarComponent} from '../../../common/components/navbar/navbar.component';

@Component({
  selector: 'app-programm',
  imports: [
    KalenderComponent,
    ProgrammeintragComponent,
    NavbarComponent
],
  templateUrl: './programm.component.html',
  styleUrl: './programm.component.css',
  standalone: true,
})
export class ProgrammComponent {

  filme?: Film[];

  constructor(private programmService: ProgrammService) {
  }

  waehleDatum(datum: Date) {
    this.programmService.holeProgramm(format(datum, "yyyy-MM-dd")).subscribe(
      data => {
        this.filme = data;
      }
    )
  }
}
