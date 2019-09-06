
import {map} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UrlServiziLocaliService } from '../core/services';
import { ServiceResponse } from '../core/models';
import { NormativaItemModel } from './normativa-item.model';


@Injectable()
export class NormativaService {

  constructor(private urlService: UrlServiziLocaliService, private http: HttpClient) {
  }

  getTop(): Observable<NormativaItemModel[]> {

    const url = this.urlService.url('normativa', ['top']);

    return this.http.get<ServiceResponse<NormativaItemModel[]>>(url).pipe(map(x => x.items));
  }

  getList(): Observable<NormativaItemModel[]> {

    const url = this.urlService.url('normativa');

    return this.http.get<ServiceResponse<NormativaItemModel[]>>(url).pipe(map(x => x.items));
  }

}
