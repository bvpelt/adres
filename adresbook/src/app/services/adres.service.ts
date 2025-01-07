import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdressesService } from '../core/modules/openapi/api/api';
import { Adresses } from '../core/modules/openapi/model/adresses';
import { Adres } from '../core/modules/openapi/model/adres';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { AdresBody, PagedAdresses } from '../core/modules/openapi';

@Injectable({
  providedIn: 'root'
})
export class AdresService {

  constructor(private api: AdressesService) {  
  }
  
  // public deleteAdres(id: number, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
  deleteAdres(user: string, password: string, id: number, xApiKey: string):  Observable<HttpResponse<any>> {
    const credentials: string = user + ':' + password;
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key' : xApiKey
    });

    const authorisation: string = 'Basic ' + btoa(credentials);
    const options:any = {
      headers: headers,
    //  httpHeaderAccept: 'application/json'
    }

    return this.api.deleteAdres(id, xApiKey, 'response', false, options);
  }
  
  // public deleteAllAdreses(xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
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

    return this.api.deleteAllAdreses(xApiKey, 'response', false, options);
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

  // public getAdresses(page: number, size: number, sort?: Array<string>, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<PagedAdresses>>;
  getAdresses(xApiKey: string, page?: number, size?: number ):  Observable<HttpResponse<PagedAdresses>> {    
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key' : xApiKey
    });

    const options:any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    return this.api.getAdresses(page!, size!, undefined, xApiKey, 'response', false, options);
  }
  
  // public patchAdres(id: number, xAPIKEY?: string, adresBody?: AdresBody, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Adres>>;
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

    return this.api.patchAdres(id, xApiKey, adresBody, 'response', false, options);
  }

  //  public postAdres(override: boolean, adresBody: AdresBody, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Adres>>;
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

    return this.api.postAdres(override, adresBody!, xApiKey, 'response', false, options);
  }

}
