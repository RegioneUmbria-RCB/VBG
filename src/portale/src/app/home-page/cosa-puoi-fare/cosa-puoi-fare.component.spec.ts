import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CosaPuoiFareComponent } from './cosa-puoi-fare.component';

describe('CosaPuoiFareComponent', () => {
  let component: CosaPuoiFareComponent;
  let fixture: ComponentFixture<CosaPuoiFareComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CosaPuoiFareComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CosaPuoiFareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
