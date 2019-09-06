import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SezioneComponent } from './sezione.component';

describe('SezioneComponent', () => {
  let component: SezioneComponent;
  let fixture: ComponentFixture<SezioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SezioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SezioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
