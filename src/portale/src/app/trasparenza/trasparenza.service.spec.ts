import { TestBed, inject } from '@angular/core/testing';

import { TrasparenzaService } from './trasparenza.service';

describe('TrasparenzaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TrasparenzaService]
    });
  });

  it('should be created', inject([TrasparenzaService], (service: TrasparenzaService) => {
    expect(service).toBeTruthy();
  }));
});
