import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NuovaDomandaComponent } from './nuova-domanda.component';

describe('NuovaDomandaComponent', () => {
  let component: NuovaDomandaComponent;
  let fixture: ComponentFixture<NuovaDomandaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NuovaDomandaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NuovaDomandaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
