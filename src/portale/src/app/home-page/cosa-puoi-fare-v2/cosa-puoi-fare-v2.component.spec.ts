import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CosaPuoiFareV2Component } from './cosa-puoi-fare-v2.component';

describe('CosaPuoiFareV2Component', () => {
  let component: CosaPuoiFareV2Component;
  let fixture: ComponentFixture<CosaPuoiFareV2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CosaPuoiFareV2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CosaPuoiFareV2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
