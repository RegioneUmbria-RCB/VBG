import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GrigliaOneriComponent } from './griglia-oneri.component';

describe('GrigliaOneriComponent', () => {
  let component: GrigliaOneriComponent;
  let fixture: ComponentFixture<GrigliaOneriComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GrigliaOneriComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GrigliaOneriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
