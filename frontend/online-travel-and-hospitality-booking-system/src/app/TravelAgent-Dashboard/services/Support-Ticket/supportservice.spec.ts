import { TestBed } from '@angular/core/testing';

import { Supportservice } from './supportservice';

describe('Supportservice', () => {
  let service: Supportservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Supportservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
