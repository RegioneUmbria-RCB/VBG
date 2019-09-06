import { TestBed, inject } from '@angular/core/testing';

import { ModulisticaService } from './modulistica.service';

describe('ModulisticaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ModulisticaService]
    });
  });

  it('should be created', inject([ModulisticaService], (service: ModulisticaService) => {
    expect(service).toBeTruthy();
  }));
});
