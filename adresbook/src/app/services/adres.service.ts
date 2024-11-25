import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdressesService } from '../core/modules/openapi/api/api';
import { Adresses } from '../core/modules/openapi/model/adresses';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdresService {

  constructor(private api: AdressesService) {  
  }

  //public getAdresses(xApiKey: string, page?: number, size?: number, sort?: string, observe: any = 'body', reportProgress: boolean = false, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<any> {
  
  getAdresses(xApiKey: string, page?: number, size?: number ): Observable<Adresses> {
    const headers: HttpHeaders = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('bvpelt:12345')
    });

    const options:any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }
    //return this.api.getAdresses(xApiKey, page, size, undefined, 'body', false, options, undefined, undefined);
    return this.api.getAdresses(xApiKey, page, size, undefined, 'body', false, options);
  }
}
