import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-badge',
  imports: [],
  templateUrl: './badge.component.html',
})
export class BadgeComponent {

  @Input()
  text: string = "";

  @Input()
  checked: boolean = false;

}
