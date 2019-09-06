
import {map} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SoftwareModulisticaModel } from './software-modulistica.model';
import { UrlServiziLocaliService } from '../core/services';
import { ServiceResponse } from '../core/models';

@Injectable()
export class ModulisticaService {

  constructor(private urlService: UrlServiziLocaliService, private http: HttpClient) {
  }

  getTop(): Observable<SoftwareModulisticaModel> {

    const url = this.urlService.url('modulistica', ['top']);

    return this.http.get<ServiceResponse<SoftwareModulisticaModel>>(url).pipe(map(x => x.items));
  }

  getList(): Observable<SoftwareModulisticaModel> {

    const url = this.urlService.url('modulistica');

    return this.http.get<ServiceResponse<SoftwareModulisticaModel>>(url).pipe(map(x => x.items));
  }


}
