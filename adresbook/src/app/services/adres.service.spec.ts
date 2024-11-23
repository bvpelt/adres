import { TestBed } from '@angular/core/testing';

import { AdresService } from './adres.service';

describe('AdresService', () => {
  let service: AdresService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdresService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
