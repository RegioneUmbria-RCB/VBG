import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterventiRegionaliComponent } from './interventi-regionali.component';

describe('InterventiRegionaliComponent', () => {
  let component: InterventiRegionaliComponent;
  let fixture: ComponentFixture<InterventiRegionaliComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InterventiRegionaliComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterventiRegionaliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
