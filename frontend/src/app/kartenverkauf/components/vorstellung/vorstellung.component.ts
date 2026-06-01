import {Component, DestroyRef, inject, OnInit, input, output, signal} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {Vorstellung} from '../../dtos/kartenverkauf';
import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {KartenverkaufService} from '../../services/kartenverkauf.service';

@Component({
  selector: 'app-vorstellung',
  imports: [
    DatePipe
  ],
  templateUrl: './vorstellung.component.html',
  styleUrl: './vorstellung.component.css',
})
export class VorstellungComponent implements OnInit {
  private router = inject(Router);
  private kartenverkaufService = inject(KartenverkaufService);
  private destroyRef = inject(DestroyRef);

  readonly vorstellungId = input.required<string>();

  readonly onVorstellungGeladen = output<Vorstellung>();

  readonly vorstellung = signal<Vorstellung | undefined>(undefined);

  ngOnInit(): void {
    this.kartenverkaufService.holeVorstellung(this.vorstellungId())
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((vorstellung: Vorstellung) => {
        this.vorstellung.set(vorstellung);
        this.onVorstellungGeladen.emit(vorstellung);
      });
  }

  zurueckZumProgramm() {
    this.router.navigate(['/programm']).then(_ => this.vorstellung.set(undefined));
  }
}
