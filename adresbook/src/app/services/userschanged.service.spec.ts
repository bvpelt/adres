import { TestBed } from '@angular/core/testing';

import { UserschangedService } from './userschanged.service';

describe('UserschangedService', () => {
  let service: UserschangedService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserschangedService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
