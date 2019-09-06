import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterventiRegionaliDetailComponent } from './interventi-regionali-detail.component';

describe('InterventiRegionaliDetailComponent', () => {
  let component: InterventiRegionaliDetailComponent;
  let fixture: ComponentFixture<InterventiRegionaliDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InterventiRegionaliDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterventiRegionaliDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
