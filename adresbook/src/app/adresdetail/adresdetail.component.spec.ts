import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdresdetailComponent } from './adresdetail.component';

describe('AdresdetailComponent', () => {
  let component: AdresdetailComponent;
  let fixture: ComponentFixture<AdresdetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdresdetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdresdetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
