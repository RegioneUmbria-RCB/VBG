import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PageInnerLayoutComponent } from './page-inner-layout.component';

describe('PageInnerLayoutComponent', () => {
  let component: PageInnerLayoutComponent;
  let fixture: ComponentFixture<PageInnerLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PageInnerLayoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PageInnerLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
