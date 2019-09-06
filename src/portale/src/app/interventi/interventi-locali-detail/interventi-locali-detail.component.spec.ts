import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterventiLocaliDetailComponent } from './interventi-locali-detail.component';

describe('InterventiLocaliDetailComponent', () => {
  let component: InterventiLocaliDetailComponent;
  let fixture: ComponentFixture<InterventiLocaliDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InterventiLocaliDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterventiLocaliDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
