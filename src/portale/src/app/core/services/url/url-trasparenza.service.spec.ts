import { TestBed, inject } from '@angular/core/testing';

import { UrlTrasparenzaService } from './url-trasparenza.service';

describe('UrlTrasparenzaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UrlTrasparenzaService]
    });
  });

  it('should be created', inject([UrlTrasparenzaService], (service: UrlTrasparenzaService) => {
    expect(service).toBeTruthy();
  }));
});
