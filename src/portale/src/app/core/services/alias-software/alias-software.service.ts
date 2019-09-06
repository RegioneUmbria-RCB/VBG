import { Injectable } from '@angular/core';

@Injectable()
export class AliasSoftwareService {

  private alias = '';
  private software = '';

  constructor() { }

  setAlias(value: string) {
    this.alias = value;
  }

  setSoftware(value: string) {
    this.software = value;
  }

  setAliasSoftware(alias: string, software: string) {
    this.setAlias(alias);
    this.setSoftware(software);
  }

  getAlias(): string {

    if (this.alias === '') {
      throw new Error('Tentativo di accedere ad un alias non inizializzato');
    }

    return this.alias;
  }

  getSoftware(): string {

    if (this.software === '') {
      throw new Error('Tentativo di accedere ad un software non inizializzato');
    }

    return this.software;
  }
}
