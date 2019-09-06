import { TestBed, inject } from '@angular/core/testing';

import { DatiComuneService } from './dati-comune.service';

describe('DatiComuneService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DatiComuneService]
    });
  });

  it('should be created', inject([DatiComuneService], (service: DatiComuneService) => {
    expect(service).toBeTruthy();
  }));
});
