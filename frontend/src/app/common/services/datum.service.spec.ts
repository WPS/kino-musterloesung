import {TestBed} from '@angular/core/testing';

import {DatumService} from './datum.service';

describe('DatumService', () => {
  let service: DatumService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DatumService]
    });
    service = TestBed.inject(DatumService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getWocheBeginnendAn', () => {
    it('should return week of days beginning monday', () => {
      const datum = new Date("2025-03-18T16:30:00");
      const expectedResult = [
        new Date("2025-03-17T00:00:00"),
        new Date("2025-03-18T00:00:00"),
        new Date("2025-03-19T00:00:00"),
        new Date("2025-03-20T00:00:00"),
        new Date("2025-03-21T00:00:00"),
        new Date("2025-03-22T00:00:00"),
        new Date("2025-03-23T00:00:00")
      ]

      const result = service.getWochentage(datum);

      expect(result).toEqual(expectedResult);
    });
  })

});
