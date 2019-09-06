import { TestBed, inject } from '@angular/core/testing';

import { UrlServiziRegionaliService } from './url-servizi-regionali.service';

describe('UrlServiziRegionaliService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UrlServiziRegionaliService]
    });
  });

  it('should be created', inject([UrlServiziRegionaliService], (service: UrlServiziRegionaliService) => {
    expect(service).toBeTruthy();
  }));
});
