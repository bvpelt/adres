import { TestBed } from '@angular/core/testing';

import { DbgmessageService } from './dbgmessage.service';

describe('DbgmessageService', () => {
  let service: DbgmessageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DbgmessageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
