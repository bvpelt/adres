import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersondetailComponent } from './persondetail.component';

describe('PersondetailComponent', () => {
  let component: PersondetailComponent;
  let fixture: ComponentFixture<PersondetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PersondetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PersondetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
