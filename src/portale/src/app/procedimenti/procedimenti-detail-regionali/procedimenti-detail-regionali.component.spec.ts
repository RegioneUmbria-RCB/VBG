import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcedimentiDetailRegionaliComponent } from './procedimenti-detail-regionali.component';

describe('ProcedimentiDetailRegionaliComponent', () => {
  let component: ProcedimentiDetailRegionaliComponent;
  let fixture: ComponentFixture<ProcedimentiDetailRegionaliComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcedimentiDetailRegionaliComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcedimentiDetailRegionaliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
