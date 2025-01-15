import { TestBed } from '@angular/core/testing';

import { PrivilegechangedService } from './privilegechanged.service';

describe('PrivilegechangedService', () => {
  let service: PrivilegechangedService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrivilegechangedService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
