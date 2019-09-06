import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SezioneInterventoComponent } from './sezione-intervento.component';

describe('SezioneInterventoComponent', () => {
  let component: SezioneInterventoComponent;
  let fixture: ComponentFixture<SezioneInterventoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SezioneInterventoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SezioneInterventoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
