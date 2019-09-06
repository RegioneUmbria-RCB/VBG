import { TestBed, inject } from '@angular/core/testing';

import { UrlLocaliService } from './url-locali.service';

describe('UrlLocaliService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UrlLocaliService]
    });
  });

  it('should be created', inject([UrlLocaliService], (service: UrlLocaliService) => {
    expect(service).toBeTruthy();
  }));
});
