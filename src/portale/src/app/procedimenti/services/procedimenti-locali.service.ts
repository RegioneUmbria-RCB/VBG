import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import {ProcedimentiBaseService} from './procedimenti-base.service';
import {UrlServiziLocaliService} from '../../core/services';

@Injectable()
export class ProcedimentiLocaliService extends ProcedimentiBaseService {

  constructor(urlService: UrlServiziLocaliService, httpClient: HttpClient) { 
    super(urlService, httpClient);
  }

}
