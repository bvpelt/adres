import { TestBed } from '@angular/core/testing';

import { AdresseschangedService } from './adresseschanged.service';

describe('AdresseschangedService', () => {
  let service: AdresseschangedService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdresseschangedService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
