import {Component, Inject, LOCALE_ID} from '@angular/core';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone: true,
})
export class AppComponent {

  constructor(@Inject(LOCALE_ID) private locale: string) {
    console.log("Current LOCALE_ID:", this.locale); // Should print: 'de-DE'
  }
}
