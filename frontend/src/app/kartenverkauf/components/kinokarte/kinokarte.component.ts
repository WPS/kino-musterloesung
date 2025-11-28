import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Kinokarte, Zahlungsvorgang} from '../../dtos/kartenverkauf';
import {KartenverkaufService} from '../../services/kartenverkauf.service';
import { DatePipe, NgClass } from '@angular/common';

@Component({
  selector: 'app-kinokarte',
  imports: [
    DatePipe,
    NgClass
],
  templateUrl: './kinokarte.component.html',
  styleUrl: './kinokarte.component.css'
})
export class KinokarteComponent implements OnInit {

  @Input()
  zahlungsbestaetigung!: Zahlungsvorgang;

  @Output()
  onKinokartenGedruckt: EventEmitter<Kinokarte[]> = new EventEmitter();

  kinokarten: Kinokarte[] | undefined;

  stacked: boolean = true;

  constructor(private kartenverkaufService: KartenverkaufService) {
  }

  ngOnInit(): void {
    this.kartenverkaufService.erstelleKinokarten(this.zahlungsbestaetigung.auftragsnummer, this.zahlungsbestaetigung.vorstellungId, this.zahlungsbestaetigung.plaetze).subscribe(
      (data: Kinokarte[]) => {
        this.kinokarten = data;
      }
    )
  }

  druckeKinokarten() {
    this.onKinokartenGedruckt.emit(this.kinokarten);
  }

  toggleStacked() {
    this.stacked = !this.stacked;
  }
}
