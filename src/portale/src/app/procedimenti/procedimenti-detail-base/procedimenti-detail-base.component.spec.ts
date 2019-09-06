import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcedimentiDetailBaseComponent } from './procedimenti-detail-base.component';

describe('ProcedimentiDetailBaseComponent', () => {
  let component: ProcedimentiDetailBaseComponent;
  let fixture: ComponentFixture<ProcedimentiDetailBaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcedimentiDetailBaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcedimentiDetailBaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
