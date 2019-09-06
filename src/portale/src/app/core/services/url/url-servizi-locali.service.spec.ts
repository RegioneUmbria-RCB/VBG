import { TestBed, inject } from '@angular/core/testing';

import { UrlServiziLocaliService } from './url-servizi-locali.service';

describe('UrlServiziLocaliService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UrlServiziLocaliService]
    });
  });

  it('should be created', inject([UrlServiziLocaliService], (service: UrlServiziLocaliService) => {
    expect(service).toBeTruthy();
  }));
});
