import {Component, input} from '@angular/core';

@Component({
  selector: 'app-badge',
  imports: [],
  templateUrl: './badge.component.html',
})
export class BadgeComponent {

  readonly text = input<string>("");

  readonly checked = input<boolean>(false);

}
