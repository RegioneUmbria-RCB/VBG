import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterventiSelezioneAreaComponent } from './interventi-selezione-area.component';

describe('InterventiSelezioneAreaComponent', () => {
  let component: InterventiSelezioneAreaComponent;
  let fixture: ComponentFixture<InterventiSelezioneAreaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InterventiSelezioneAreaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterventiSelezioneAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
