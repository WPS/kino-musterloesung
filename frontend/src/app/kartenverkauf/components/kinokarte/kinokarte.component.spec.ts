import {ComponentFixture, TestBed} from '@angular/core/testing';

import {KinokarteComponent} from './kinokarte.component';
import {provideHttpClient} from '@angular/common/http';
import {provideHttpClientTesting} from '@angular/common/http/testing';
import {Waehrung} from '../../dtos/kartenverkauf';

describe('KinokarteComponent', () => {
  let component: KinokarteComponent;
  let fixture: ComponentFixture<KinokarteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        KinokarteComponent
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(KinokarteComponent);
    component = fixture.componentInstance;
    component.zahlungsbestaetigung = {
      auftragsnummer: 'A-2025-000123',
      vorstellungId: '123e4567-e89b-12d3-a456-426614174000',
      plaetze: {plaetze: [{reihe: 1, platz: 2}]},
      betrag: {
        betrag: 36.0,
        waehrung: Waehrung.EUR,
      },
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
