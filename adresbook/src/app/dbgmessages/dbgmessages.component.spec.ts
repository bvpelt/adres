import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DbgmessagesComponent } from './dbgmessages.component';

describe('DbgmessagesComponent', () => {
  let component: DbgmessagesComponent;
  let fixture: ComponentFixture<DbgmessagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DbgmessagesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DbgmessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
