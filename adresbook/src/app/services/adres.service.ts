import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdressesService } from '../core/modules/openapi/api/api';
import { Adresses } from '../core/modules/openapi/model/adresses';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdresService {

  constructor(private api: AdressesService) {  
  }
    
  getAdresses(user: string, password: string, xApiKey: string, page?: number, size?: number ):  Observable<HttpResponse<Adresses>> {
    const credentials: string = user + ':' + password;
    const headers: HttpHeaders = new HttpHeaders({
      'Authorization': 'Basic ' + btoa(credentials)
    });

    const options:any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    return this.api.getAdresses(xApiKey, page, size, undefined, 'response', false, options);
  }
}
