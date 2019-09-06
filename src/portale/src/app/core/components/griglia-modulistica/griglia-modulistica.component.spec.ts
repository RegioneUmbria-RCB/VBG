import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GrigliaModulisticaComponent } from './griglia-modulistica.component';

describe('GrigliaModulisticaComponent', () => {
  let component: GrigliaModulisticaComponent;
  let fixture: ComponentFixture<GrigliaModulisticaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GrigliaModulisticaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GrigliaModulisticaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
