import { UrlParams } from './url-params';
import { Injectable } from '@angular/core';
import { BaseUrlservice } from './base-url.service';
import { ConfigurationService } from '../configuration/configuration.service';

@Injectable()
export class UrlServiziRegionaliService extends BaseUrlservice {

  constructor(configurationService: ConfigurationService) {
    super(() => new UrlParams( configurationService.getConfiguration().serviziRegionali.baseUrl,
          configurationService.getConfiguration().serviziRegionali.alias,
          configurationService.getConfiguration().serviziRegionali.software));
  }

}
