import { TestBed } from '@angular/core/testing';

import { HotelManager } from './hotel-manager';

describe('HotelManager', () => {
  let service: HotelManager;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HotelManager);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
