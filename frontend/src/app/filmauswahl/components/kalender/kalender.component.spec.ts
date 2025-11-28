import {ComponentFixture, TestBed} from '@angular/core/testing';

import {KalenderComponent} from './kalender.component';
import {provideHttpClient} from '@angular/common/http';
import {provideHttpClientTesting} from '@angular/common/http/testing';
import {provideRouter} from '@angular/router';

describe('KalenderComponent', () => {
  let component: KalenderComponent;
  let fixture: ComponentFixture<KalenderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting(),
        KalenderComponent
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(KalenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
