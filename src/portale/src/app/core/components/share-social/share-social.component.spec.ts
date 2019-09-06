import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShareSocialComponent } from './share-social.component';

describe('ShareSocialComponent', () => {
  let component: ShareSocialComponent;
  let fixture: ComponentFixture<ShareSocialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShareSocialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShareSocialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
