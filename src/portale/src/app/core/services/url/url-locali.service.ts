import { Injectable } from '@angular/core';
import { BaseUrlservice } from './base-url.service';
import { AliasSoftwareService } from '../alias-software';
import { UrlParams } from './url-params';

@Injectable()
export class UrlLocaliService extends BaseUrlservice {

  constructor(aliasSoftwareService: AliasSoftwareService) {
    super(() => new UrlParams('', aliasSoftwareService.getAlias(), aliasSoftwareService.getSoftware()));
  }


}
