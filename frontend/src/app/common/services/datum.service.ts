import {Injectable} from '@angular/core';
import {addDays, startOfWeek} from 'date-fns';

@Injectable({
  providedIn: 'root'
})
export class DatumService {
  
  constructor() {
  }

  getWochentage(date: Date) {
    const monday = startOfWeek(date, {weekStartsOn: 1});
    return Array.from({length: 7}, (_, i) => addDays(monday, i));
  }
}
