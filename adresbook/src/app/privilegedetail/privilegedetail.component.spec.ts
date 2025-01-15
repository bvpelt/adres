import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrivilegedetailComponent } from './privilegedetail.component';

describe('PrivilegedetailComponent', () => {
  let component: PrivilegedetailComponent;
  let fixture: ComponentFixture<PrivilegedetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PrivilegedetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PrivilegedetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
