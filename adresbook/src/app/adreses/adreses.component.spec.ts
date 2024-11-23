import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdresesComponent } from './adreses.component';

describe('AdresesComponent', () => {
  let component: AdresesComponent;
  let fixture: ComponentFixture<AdresesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdresesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdresesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
