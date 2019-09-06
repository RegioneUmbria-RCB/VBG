import { TestBed, inject } from '@angular/core/testing';

import { ProcedimentiService } from './procedimenti.service';

describe('ProcedimentiService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ProcedimentiService]
    });
  });

  it('should be created', inject([ProcedimentiService], (service: ProcedimentiService) => {
    expect(service).toBeTruthy();
  }));
});
