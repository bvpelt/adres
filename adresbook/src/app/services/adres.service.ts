import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdressesService } from '../core/modules/openapi/api/api';
import { Adresses } from '../core/modules/openapi/model/adresses';
import { Adres } from '../core/modules/openapi/model/adres';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { AdresBody } from '../core/modules/openapi';

@Injectable({
  providedIn: 'root'
})
export class AdresService {

  constructor(private api: AdressesService) {  
  }
  
  // public deleteAdres(id: number, xApiKey: string, authorization: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
  deleteAdres(user: string, password: string, id: number, xApiKey: string, page?: number, size?: number ):  Observable<HttpResponse<any>> {
    const credentials: string = user + ':' + password;
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key' : xApiKey
    });

    const authorisation: string = 'Basic ' + btoa(credentials);
    const options:any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    return this.api.deleteAdres(id, xApiKey, authorisation, 'response', false, options);
  }
  
  // public deleteAllAdreses(xApiKey: string, authorization: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
  deleteAllAdreses(user: string, password: string, xApiKey: string, page?: number, size?: number ):  Observable<HttpResponse<any>> {
    const credentials: string = user + ':' + password;
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key' : xApiKey
    });

    const authorisation: string = 'Basic ' + btoa(credentials);
    const options:any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    return this.api.deleteAllAdreses(xApiKey, authorisation, 'response', false, options);
  }

  //public getAdres(id: number, xApiKey: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Adres>>;
  getAdres(id: number, xApiKey: string):  Observable<HttpResponse<Adres>> {    
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key' : xApiKey
    });

    const options:any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    return this.api.getAdres(id, xApiKey, 'response', false, options);
  }

  // public getAdresses(xApiKey: string, page?: number, size?: number, sort?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Adresses>>;    
  getAdresses(xApiKey: string, page?: number, size?: number ):  Observable<HttpResponse<Adresses>> {    
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key' : xApiKey
    });

    const options:any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    return this.api.getAdresses(xApiKey, page, size, undefined, 'response', false, options);
  }
  
  //  public patchAdres(id: number, xApiKey: string, authorization: string, adresBody?: AdresBody, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Adres>>;
  patchAdres(user: string, password: string, id: number, xApiKey: string, adresBody?: AdresBody ):  Observable<HttpResponse<Adres>> {
    const credentials: string = user + ':' + password;
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key' : xApiKey
    });
    const authorisation: string = 'Basic ' + btoa(credentials);
    const options:any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    return this.api.patchAdres(id, xApiKey, authorisation, adresBody, 'response', false, options);
  }

  //   public postAdres(xApiKey: string, authorization: string, override?: boolean, adresBody?: AdresBody, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Adres>>;
  postAdres(user: string, password: string, xApiKey: string, override: boolean, adresBody?: AdresBody ):  Observable<HttpResponse<Adres>> {
    const credentials: string = user + ':' + password;
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key' : xApiKey
    });
    const authorisation: string = 'Basic ' + btoa(credentials);
    const options:any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    return this.api.postAdres(xApiKey, authorisation, override, adresBody, 'response', false, options);
  }

}
