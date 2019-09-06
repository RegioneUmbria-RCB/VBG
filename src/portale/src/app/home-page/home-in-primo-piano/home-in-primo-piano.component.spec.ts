import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeInPrimoPianoComponent } from './home-in-primo-piano.component';

describe('HomeInPrimoPianoComponent', () => {
  let component: HomeInPrimoPianoComponent;
  let fixture: ComponentFixture<HomeInPrimoPianoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeInPrimoPianoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeInPrimoPianoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
