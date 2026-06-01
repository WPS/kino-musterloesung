import {Component, inject, LOCALE_ID} from '@angular/core';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  private locale = inject(LOCALE_ID);

  constructor() {
    console.log("Current LOCALE_ID:", this.locale); // Should print: 'de-DE'
  }
}
