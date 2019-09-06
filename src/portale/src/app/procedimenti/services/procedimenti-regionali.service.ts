import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { ProcedimentiBaseService } from './procedimenti-base.service';
import { UrlServiziRegionaliService } from '../../core/services';

@Injectable()
export class ProcedimentiRegionaliService extends ProcedimentiBaseService {

    constructor(urlService: UrlServiziRegionaliService, httpClient: HttpClient) {
        super(urlService, httpClient);
    }

}
