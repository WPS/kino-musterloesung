import {ComponentFixture, TestBed} from '@angular/core/testing';

import {KartenverkaufComponent} from './kartenverkauf.component';
import {provideHttpClient} from '@angular/common/http';
import {provideHttpClientTesting} from '@angular/common/http/testing';
import {KartenverkaufService} from '../../services/kartenverkauf.service';
import {provideRouter} from '@angular/router';

describe('KartenverkaufComponent', () => {
  let component: KartenverkaufComponent;
  let fixture: ComponentFixture<KartenverkaufComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting(),
        KartenverkaufService
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KartenverkaufComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
