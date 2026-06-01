import {Component, DestroyRef, inject, signal} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
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
})
export class ProgrammComponent {
  private programmService = inject(ProgrammService);
  private destroyRef = inject(DestroyRef);

  readonly filme = signal<Film[] | undefined>(undefined);

  waehleDatum(datum: Date) {
    this.programmService.holeProgramm(format(datum, "yyyy-MM-dd"))
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(data => this.filme.set(data));
  }
}
