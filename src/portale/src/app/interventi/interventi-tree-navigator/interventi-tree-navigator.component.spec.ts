import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterventiTreeNavigatorComponent } from './interventi-tree-navigator.component';

describe('InterventiTreeNavigatorComponent', () => {
  let component: InterventiTreeNavigatorComponent;
  let fixture: ComponentFixture<InterventiTreeNavigatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InterventiTreeNavigatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterventiTreeNavigatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
