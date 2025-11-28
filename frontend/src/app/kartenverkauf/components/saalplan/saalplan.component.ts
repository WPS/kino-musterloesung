import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Platz, Saalplan, Vorstellung, ZusammenhaengendePlaetze} from '../../dtos/kartenverkauf';

import {KartenverkaufService} from '../../services/kartenverkauf.service';

@Component({
  selector: 'app-saalplan',
  imports: [],
  templateUrl: './saalplan.component.html',
  styleUrl: './saalplan.component.css'
})
export class SaalplanComponent implements OnInit {

  @Input({required: true})
  vorstellung!: Vorstellung;

  @Input({required: true})
  platzanzahl!: number;

  @Output()
  onPlatzwahlBestaetigt = new EventEmitter<ZusammenhaengendePlaetze>();

  saalplan: Saalplan | undefined;
  angebotenePlaetze: ZusammenhaengendePlaetze | undefined;
  gewaehltePlaetze: ZusammenhaengendePlaetze | undefined;
  gehovertePlaetze: ZusammenhaengendePlaetze | undefined;
  fertig: boolean = false;

  radius = 20;
  spacing = 53;
  rowSpacing = 80;
  startY = 70;

  constructor(private kartenverkaufService: KartenverkaufService) {
  }

  ngOnInit(): void {
    this.kartenverkaufService.holeSaalplan(this.vorstellung!.uuid).subscribe(
      (data: Saalplan) => {
        this.saalplan = data;
      }
    )
    this.kartenverkaufService.sucheZusammenhaengendePlaetze(this.vorstellung.uuid, this.platzanzahl).subscribe(
      (data: ZusammenhaengendePlaetze) => {
        this.angebotenePlaetze = data;
        this.gewaehltePlaetze = data;
      }
    )
  }

  get plaetzeGeladen(): boolean {
    return this.angebotenePlaetze !== undefined
  };

  get plaetzeGefunden(): boolean {
    return this.angebotenePlaetze?.plaetze.length == this.platzanzahl
  }

  get saalplanGeladen(): boolean {
    return this.saalplan !== undefined && this.angebotenePlaetze !== undefined
  };

  platzwahlBestaetigen() {
    this.fertig = true;
    this.onPlatzwahlBestaetigt.emit(this.gewaehltePlaetze!);
  }


  get saalplanBreite(): number {
    const maxLength = Math.max(...this.saalplan!.plaetze.map(reihe => reihe.length));
    return this.spacing * maxLength + this.radius * 2;
  }

  get saalplanHoehe(): number {
    return this.startY + (this.saalplan!.plaetze.length) * this.rowSpacing;
  }

  get leinwandBreite() {
    return this.saalplanBreite - ((this.saalplanBreite / 8) * 2);
  }

  get leinwandPosition() {
    return this.saalplanBreite / 8;
  }

  get platzbelegungenReihe(): Platz[][] {
    return this.saalplan?.plaetze ?? [];
  }

  getStartX(row: Platz[]): number {
    const rowWidth = (row.length) * this.spacing;
    return (this.saalplanBreite - rowWidth);
  }

  getFarbeFuerPlatzbelegung(platz: Platz): String {
    if (this.gehovertePlaetze?.plaetze.some(p => p.platz === platz.platz && p.reihe === platz.reihe)) {
      return 'yellow';
    }

    if (this.gewaehltePlaetze?.plaetze.some(p => p.platz === platz.platz && p.reihe === platz.reihe)) {
      return 'lightgreen';
    }

    if (platz.istFrei) {
      return 'white';
    }

    return 'lightgray';
  }

  get plaetzeString(): string {
    if (this.gewaehltePlaetze?.plaetze?.length == 1) {
      return `Reihe ${this.gewaehltePlaetze?.plaetze?.[0].reihe} ⋅ Platz ${this.gewaehltePlaetze?.plaetze?.[0].platz}`;
    } else {
      return `Reihe ${this.gewaehltePlaetze?.plaetze?.[0].reihe} ⋅ Plätze ${this.gewaehltePlaetze?.plaetze?.[0].platz} - ${this.gewaehltePlaetze?.plaetze?.at(-1)?.platz}`;
    }
  }

  platzEnter(rowIndex: number, columnIndex: number) {
    let block = this.ermittleFreienBlock(rowIndex, columnIndex);
    if (block) {
      this.gehovertePlaetze = block;
    }
  }

  platzLeave() {
    this.gehovertePlaetze = undefined;
  }

  platzClick(rowIndex: number, columnIndex: number) {
    let block = this.ermittleFreienBlock(rowIndex, columnIndex);
    if (block) {
      this.gewaehltePlaetze = block;
    }
  }

  private ermittleFreienBlock(rowIndex: number, columnIndex: number): ZusammenhaengendePlaetze | undefined {
    let row = this.saalplan!.plaetze[rowIndex];

    if (!row[columnIndex].istFrei) return undefined;

    let li = columnIndex;
    let ri = columnIndex;
    let n = 1;
    let steptaken = true;

    while (steptaken) {
      steptaken = false;
      if (n == this.platzanzahl) break;
      if (ri < row.length - 1 && row[ri + 1].istFrei) {
        ri++;
        n++;
        steptaken = true;
      }
      if (n == this.platzanzahl) break;
      if (li > 0 && row[li - 1].istFrei) {
        li--;
        n++;
        steptaken = true;
      }
    }

    if (n == this.platzanzahl) {
      return {plaetze: row.slice(li, ri + 1)};
    }

    return undefined;
  }
}
