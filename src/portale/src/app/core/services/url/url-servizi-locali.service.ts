import { Injectable } from '@angular/core';
import { BaseUrlservice } from './base-url.service';
import { ConfigurationService } from '../configuration/configuration.service';
import { UrlParams } from './url-params';

@Injectable()
export class UrlServiziLocaliService extends BaseUrlservice {

  constructor(configurationService: ConfigurationService) {
    super(()  => new UrlParams( configurationService.getConfiguration().backend.baseUrl,
          configurationService.getConfiguration().backend.alias,
          configurationService.getConfiguration().backend.software));
  }

}
