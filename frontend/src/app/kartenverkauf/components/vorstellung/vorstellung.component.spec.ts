import {ComponentFixture, TestBed} from '@angular/core/testing';

import {VorstellungComponent} from './vorstellung.component';
import {provideHttpClient} from '@angular/common/http';
import {provideHttpClientTesting} from '@angular/common/http/testing';

describe('VorstellungComponent', () => {
  let component: VorstellungComponent;
  let fixture: ComponentFixture<VorstellungComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        VorstellungComponent
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(VorstellungComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
