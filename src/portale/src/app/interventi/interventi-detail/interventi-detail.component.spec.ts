import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterventiDetailComponent } from './interventi-detail.component';

describe('InterventiDetailComponent', () => {
  let component: InterventiDetailComponent;
  let fixture: ComponentFixture<InterventiDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InterventiDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterventiDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
