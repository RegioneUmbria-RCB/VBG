import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NormativaListComponent } from './normativa-list.component';

describe('NormativaListComponent', () => {
  let component: NormativaListComponent;
  let fixture: ComponentFixture<NormativaListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NormativaListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NormativaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
