import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

import { UrlServiziRegionaliService } from '../../../core';
import { InterventiBaseService } from '../interventi-base.service';

@Injectable()
export class InterventiRegionaliService extends InterventiBaseService {

  constructor(urlService: UrlServiziRegionaliService, http: HttpClient) {
    super(urlService, http);
  }
}
