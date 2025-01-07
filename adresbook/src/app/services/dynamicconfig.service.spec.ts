import { TestBed } from '@angular/core/testing';

import { DynamicconfigService } from './dynamicconfig.service';

describe('DynamicconfigService', () => {
  let service: DynamicconfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DynamicconfigService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
