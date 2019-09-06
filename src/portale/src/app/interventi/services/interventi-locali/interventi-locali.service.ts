import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

import { UrlServiziLocaliService } from '../../../core';
import { InterventiBaseService } from '../interventi-base.service';

@Injectable()
export class InterventiLocaliService extends InterventiBaseService {

  constructor(urlServiziLocaliService: UrlServiziLocaliService, http: HttpClient) {
    super(urlServiziLocaliService, http);
  }
}
