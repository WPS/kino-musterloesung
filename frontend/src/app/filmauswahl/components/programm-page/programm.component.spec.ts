import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ProgrammComponent} from './programm.component';
import {provideHttpClient} from '@angular/common/http';
import {provideHttpClientTesting} from '@angular/common/http/testing';
import {provideRouter} from '@angular/router';

describe('ProgrammComponent', () => {
  let component: ProgrammComponent;
  let fixture: ComponentFixture<ProgrammComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting(),
        ProgrammComponent
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ProgrammComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
