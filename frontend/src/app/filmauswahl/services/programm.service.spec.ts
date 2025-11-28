import {TestBed} from '@angular/core/testing';

import {ProgrammService} from './programm.service';
import {provideHttpClient, withFetch} from '@angular/common/http';

describe('ProgrammService', () => {
  let service: ProgrammService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ProgrammService,
        provideHttpClient(withFetch())
      ]
    });
    service = TestBed.inject(ProgrammService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
