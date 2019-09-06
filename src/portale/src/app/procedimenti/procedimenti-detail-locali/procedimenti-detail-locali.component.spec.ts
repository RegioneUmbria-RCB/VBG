import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcedimentiDetailLocaliComponent } from './procedimenti-detail-locali.component';

describe('ProcedimentiDetailLocaliComponent', () => {
  let component: ProcedimentiDetailLocaliComponent;
  let fixture: ComponentFixture<ProcedimentiDetailLocaliComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcedimentiDetailLocaliComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcedimentiDetailLocaliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
