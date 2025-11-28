import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
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
  standalone: true,
})
export class VorstellungComponent implements OnInit {

  @Input({required: true})
  vorstellungId!: string;

  @Output() onVorstellungGeladen: EventEmitter<Vorstellung> = new EventEmitter();

  vorstellung: Vorstellung | undefined;

  constructor(private router: Router, private kartenverkaufService: KartenverkaufService) {
  }

  ngOnInit(): void {
    this.kartenverkaufService.holeVorstellung(this.vorstellungId).subscribe((vorstellung: Vorstellung) => {
      this.vorstellung = vorstellung
      this.onVorstellungGeladen.emit(vorstellung);
    });
  }

  zurueckZumProgramm() {
    this.router.navigate(['/programm']).then(_ => this.vorstellung = undefined);
  }
}
