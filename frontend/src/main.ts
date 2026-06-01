import {bootstrapApplication} from '@angular/platform-browser';
import {provideHttpClient, withFetch} from '@angular/common/http';
import {appConfig} from './app/app.config';
import {AppComponent} from './app/app.component';
import {registerLocaleData} from '@angular/common';
import de from '@angular/common/locales/de';
import {LOCALE_ID} from '@angular/core';

registerLocaleData(de);

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withFetch()),
    {provide: LOCALE_ID, useValue: 'de'},
    ...appConfig.providers
  ]
})
  .catch((err) => console.error(err));
