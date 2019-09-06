import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrasparenzaDetailComponent } from './trasparenza-detail.component';

describe('TrasparenzaDetailComponent', () => {
  let component: TrasparenzaDetailComponent;
  let fixture: ComponentFixture<TrasparenzaDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrasparenzaDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrasparenzaDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
