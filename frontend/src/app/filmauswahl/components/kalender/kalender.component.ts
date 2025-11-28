import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import { DatePipe, NgClass } from '@angular/common';
import {DatumService} from '../../../common/services/datum.service';
import {format, isBefore, startOfDay} from 'date-fns';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {distinctUntilChanged, map} from 'rxjs';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-kalender',
  imports: [
    DatePipe,
    FormsModule,
    NgClass
],
  providers: [DatePipe],
  templateUrl: './kalender.component.html',
  styleUrl: './kalender.component.css'
})
export class KalenderComponent implements OnInit {

  constructor(private datumService: DatumService, private router: Router, private readonly activatedRoute: ActivatedRoute) {

  }

  today: Date = startOfDay(new Date("2025-03-19"));

  selectableDates: Date[] = [this.today];

  selectedDate: Date = this.today;

  @Output()
  dateSelected = new EventEmitter<Date>();

  ngOnInit(): void {
    this.selectableDates = this.datumService.getWochentage(this.today);
    this.activatedRoute.queryParamMap.pipe(
      map((queryParamMap) => queryParamMap.get('datum')),
      map((value) => value ? new Date(value) : this.today),
      distinctUntilChanged())
      .subscribe(value => {
        this.selectedDate = value ?? this.today;
        this.selectedDate = startOfDay(this.selectedDate);
        this.dateSelected.emit(this.selectedDate);
      });
  }

  selectDate(datum: Date): void {
    this.navigate({'datum': format(datum, 'yyyy-MM-dd')});
  }

  isBeforeToday(date: Date): boolean {
    return isBefore(startOfDay(date), this.today);
  }

  navigate(queryParams: Params): Promise<boolean> {
    return this.router.navigate([], {
      queryParams, queryParamsHandling: 'merge', replaceUrl: true,
    });
  }
}
