import { TestBed } from '@angular/core/testing';

import { PersonschangedService } from './personschanged.service';

describe('PersonschangedService', () => {
  let service: PersonschangedService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonschangedService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
