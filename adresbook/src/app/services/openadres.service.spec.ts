import { TestBed } from '@angular/core/testing';

import { OpenadresService } from './openadres.service';

describe('OpenadresService', () => {
  let service: OpenadresService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OpenadresService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
