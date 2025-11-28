import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SaalplanComponent} from './saalplan.component';
import {provideHttpClient} from '@angular/common/http';
import {provideHttpClientTesting} from '@angular/common/http/testing';

describe('SaalplanComponent', () => {
  let component: SaalplanComponent;
  let fixture: ComponentFixture<SaalplanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        SaalplanComponent
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaalplanComponent);
    component = fixture.componentInstance;
    component.vorstellung = {
      uuid: '123e4567-e89b-12d3-a456-426614174000',
      beginn: '2025-03-16T20:00:00',
      saal: 'Saal 1',
      film: 'Inception',
    };
    component.platzanzahl = 4;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
