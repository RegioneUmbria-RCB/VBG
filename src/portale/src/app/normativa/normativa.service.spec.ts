import { TestBed, inject } from '@angular/core/testing';

import { NormativaService } from './normativa.service';

describe('NormativaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NormativaService]
    });
  });

  it('should be created', inject([NormativaService], (service: NormativaService) => {
    expect(service).toBeTruthy();
  }));
});
