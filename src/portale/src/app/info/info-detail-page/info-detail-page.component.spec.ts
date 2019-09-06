import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoDetailPageComponent } from './info-detail-page.component';

describe('InfoDetailPageComponent', () => {
  let component: InfoDetailPageComponent;
  let fixture: ComponentFixture<InfoDetailPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InfoDetailPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfoDetailPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
