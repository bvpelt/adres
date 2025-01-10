import { TestBed } from '@angular/core/testing';

import { RoleschangedService } from './roleschanged.service';

describe('RoleschangedService', () => {
  let service: RoleschangedService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoleschangedService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
