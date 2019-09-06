import { TestBed, inject } from '@angular/core/testing';

import { ProcedimentiLocaliService } from './procedimenti-locali.service';

describe('ProcedimentiLocaliService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ProcedimentiLocaliService]
    });
  });

  it('should be created', inject([ProcedimentiLocaliService], (service: ProcedimentiLocaliService) => {
    expect(service).toBeTruthy();
  }));
});
