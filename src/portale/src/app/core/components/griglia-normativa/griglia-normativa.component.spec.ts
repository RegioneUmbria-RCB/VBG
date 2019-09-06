import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GrigliaNormativaComponent } from './griglia-normativa.component';

describe('GrigliaNormativaComponent', () => {
  let component: GrigliaNormativaComponent;
  let fixture: ComponentFixture<GrigliaNormativaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GrigliaNormativaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GrigliaNormativaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
