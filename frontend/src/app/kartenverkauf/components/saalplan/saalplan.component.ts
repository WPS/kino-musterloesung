import {Component, computed, DestroyRef, inject, input, OnInit, output, signal} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {Platz, Saalplan, Vorstellung, ZusammenhaengendePlaetze} from '../../dtos/kartenverkauf';

import {KartenverkaufService} from '../../services/kartenverkauf.service';

@Component({
  selector: 'app-saalplan',
  imports: [],
  templateUrl: './saalplan.component.html',
  styleUrl: './saalplan.component.css'
})
export class SaalplanComponent implements OnInit {
  private kartenverkaufService = inject(KartenverkaufService);
  private destroyRef = inject(DestroyRef);

  readonly vorstellung = input.required<Vorstellung>();

  readonly platzanzahl = input.required<number>();

  readonly onPlatzwahlBestaetigt = output<ZusammenhaengendePlaetze>();

  readonly saalplan = signal<Saalplan | undefined>(undefined);
  readonly angebotenePlaetze = signal<ZusammenhaengendePlaetze | undefined>(undefined);
  readonly gewaehltePlaetze = signal<ZusammenhaengendePlaetze | undefined>(undefined);
  readonly gehovertePlaetze = signal<ZusammenhaengendePlaetze | undefined>(undefined);
  readonly fertig = signal(false);

  radius = 20;
  spacing = 53;
  rowSpacing = 80;
  startY = 70;

  readonly plaetzeGeladen = computed(() => this.angebotenePlaetze() !== undefined);

  readonly plaetzeGefunden = computed(
    () => this.angebotenePlaetze()?.plaetze.length === this.platzanzahl()
  );

  readonly saalplanGeladen = computed(
    () => this.saalplan() !== undefined && this.angebotenePlaetze() !== undefined
  );

  readonly saalplanBreite = computed(() => {
    const s = this.saalplan();
    if (!s) return 0;
    const maxLength = Math.max(...s.plaetze.map(reihe => reihe.length));
    return this.spacing * maxLength + this.radius * 2;
  });

  readonly saalplanHoehe = computed(() => {
    const s = this.saalplan();
    if (!s) return 0;
    return this.startY + s.plaetze.length * this.rowSpacing;
  });

  readonly leinwandBreite = computed(() => this.saalplanBreite() - ((this.saalplanBreite() / 8) * 2));

  readonly leinwandPosition = computed(() => this.saalplanBreite() / 8);

  readonly platzbelegungenReihe = computed<Platz[][]>(() => this.saalplan()?.plaetze ?? []);

  readonly plaetzeString = computed(() => {
    const gewaehlt = this.gewaehltePlaetze();
    if (!gewaehlt || gewaehlt.plaetze.length === 0) return '';
    if (gewaehlt.plaetze.length === 1) {
      return `Reihe ${gewaehlt.plaetze[0].reihe} ⋅ Platz ${gewaehlt.plaetze[0].platz}`;
    }
    return `Reihe ${gewaehlt.plaetze[0].reihe} ⋅ Plätze ${gewaehlt.plaetze[0].platz} - ${gewaehlt.plaetze.at(-1)?.platz}`;
  });

  ngOnInit(): void {
    this.kartenverkaufService.holeSaalplan(this.vorstellung().uuid)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((data: Saalplan) => this.saalplan.set(data));

    this.kartenverkaufService.sucheZusammenhaengendePlaetze(this.vorstellung().uuid, this.platzanzahl())
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((data: ZusammenhaengendePlaetze) => {
        this.angebotenePlaetze.set(data);
        this.gewaehltePlaetze.set(data);
      });
  }

  platzwahlBestaetigen() {
    this.fertig.set(true);
    this.onPlatzwahlBestaetigt.emit(this.gewaehltePlaetze()!);
  }

  getStartX(row: Platz[]): number {
    const rowWidth = (row.length) * this.spacing;
    return (this.saalplanBreite() - rowWidth);
  }

  getFarbeFuerPlatzbelegung(platz: Platz): String {
    if (this.gehovertePlaetze()?.plaetze.some(p => p.platz === platz.platz && p.reihe === platz.reihe)) {
      return 'yellow';
    }

    if (this.gewaehltePlaetze()?.plaetze.some(p => p.platz === platz.platz && p.reihe === platz.reihe)) {
      return 'lightgreen';
    }

    if (platz.istFrei) {
      return 'white';
    }

    return 'lightgray';
  }

  platzEnter(rowIndex: number, columnIndex: number) {
    const block = this.ermittleFreienBlock(rowIndex, columnIndex);
    if (block) {
      this.gehovertePlaetze.set(block);
    }
  }

  platzLeave() {
    this.gehovertePlaetze.set(undefined);
  }

  platzClick(rowIndex: number, columnIndex: number) {
    const block = this.ermittleFreienBlock(rowIndex, columnIndex);
    if (block) {
      this.gewaehltePlaetze.set(block);
    }
  }

  private ermittleFreienBlock(rowIndex: number, columnIndex: number): ZusammenhaengendePlaetze | undefined {
    let row = this.saalplan()!.plaetze[rowIndex];

    if (!row[columnIndex].istFrei) return undefined;

    let li = columnIndex;
    let ri = columnIndex;
    let n = 1;
    let steptaken = true;

    const platzanzahl = this.platzanzahl();
    while (steptaken) {
      steptaken = false;
      if (n == platzanzahl) break;
      if (ri < row.length - 1 && row[ri + 1].istFrei) {
        ri++;
        n++;
        steptaken = true;
      }
      if (n == platzanzahl) break;
      if (li > 0 && row[li - 1].istFrei) {
        li--;
        n++;
        steptaken = true;
      }
    }

    if (n == platzanzahl) {
      return {plaetze: row.slice(li, ri + 1)};
    }

    return undefined;
  }
}
