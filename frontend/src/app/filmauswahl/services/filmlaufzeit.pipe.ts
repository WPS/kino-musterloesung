import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'durationFormat'
})
export class DurationPipe implements PipeTransform {
  transform(minutes: number): string {
    if (!minutes && minutes !== 0) return '';

    const hours = Math.floor(minutes / 60);
    const mins = minutes % 60;

    return `${hours > 0 ? hours + ' Std ' : ''}${mins > 0 ? mins + ' Min' : ''}`.trim();
  }
}
