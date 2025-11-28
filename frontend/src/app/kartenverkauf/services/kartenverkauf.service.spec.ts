import {TestBed} from '@angular/core/testing';

import {KartenverkaufService} from './kartenverkauf.service';
import {provideHttpClientTesting} from '@angular/common/http/testing';
import {provideHttpClient} from '@angular/common/http';

describe('KartenverkaufService', () => {
  let service: KartenverkaufService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        KartenverkaufService
      ]
    });
    service = TestBed.inject(KartenverkaufService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
