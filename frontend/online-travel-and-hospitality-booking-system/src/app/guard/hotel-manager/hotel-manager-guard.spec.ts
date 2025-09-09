import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { hotelManagerGuard } from './hotel-manager-guard';

describe('hotelManagerGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => hotelManagerGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
