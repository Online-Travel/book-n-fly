import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { travelerGuard } from './traveler-guard';

describe('travelerGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => travelerGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
