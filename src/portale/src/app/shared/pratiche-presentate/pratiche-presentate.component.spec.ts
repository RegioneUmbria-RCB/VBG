import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PratichePresentateComponent } from './pratiche-presentate.component';

describe('PratichePresentateComponent', () => {
  let component: PratichePresentateComponent;
  let fixture: ComponentFixture<PratichePresentateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PratichePresentateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PratichePresentateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
