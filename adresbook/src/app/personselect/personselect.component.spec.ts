import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonselectComponent } from './personselect.component';

describe('PersonselectComponent', () => {
  let component: PersonselectComponent;
  let fixture: ComponentFixture<PersonselectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PersonselectComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PersonselectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
