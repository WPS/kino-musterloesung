import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ZahlungComponent} from './zahlung.component';
import {provideHttpClient} from '@angular/common/http';
import {provideHttpClientTesting} from '@angular/common/http/testing';

describe('ZahlungComponent', () => {
  let component: ZahlungComponent;
  let fixture: ComponentFixture<ZahlungComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        ZahlungComponent
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ZahlungComponent);
    component = fixture.componentInstance;
    component.vorstellung = {
      uuid: '123e4567-e89b-12d3-a456-426614174000',
      beginn: '2025-03-16T20:00:00',
      saal: 'Saal 1',
      film: 'Inception',
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
