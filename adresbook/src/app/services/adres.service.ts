import { Inject, Injectable, Optional } from '@angular/core';
import { Observable } from 'rxjs';
import { AdressesService } from '../core/modules/openapi/api/api';
import { Adres } from '../core/modules/openapi/model/adres';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AdresBody, BASE_PATH, Configuration, PagedAdresses, PagedPersons } from '../core/modules/openapi';
import { DynamicconfigService } from './dynamicconfig.service';

@Injectable({
  providedIn: 'root'
})
export class AdresService {

  private api: AdressesService | undefined;

  constructor(
    private http: HttpClient,
    @Optional() @Inject(BASE_PATH) basePath: string | string[],
    private dynamicConfigService: DynamicconfigService
  ) {
    var basePathToUse = Array.isArray(BASE_PATH) ? BASE_PATH[0] : BASE_PATH;
     var config: Configuration = new Configuration({
          basePath: basePathToUse
        });
    this.api = new AdressesService(http, basePath, config);
    this.dynamicConfigService.config$.subscribe((config: any) => {
      if (config) {
        this.api = new AdressesService(http, basePath, config);
      }
    });
  }

  // public deleteAdres(id: number, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
  deleteAdres(id: number, xApiKey: string): Observable<HttpResponse<any>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers
    }

    if (this.api != undefined) {
      return this.api.deleteAdres(id, xApiKey, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public deleteAllAdreses(xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
  deleteAllAdreses(xApiKey: string, page?: number, size?: number): Observable<HttpResponse<any>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers
    }

    if (this.api != undefined) {
      return this.api.deleteAllAdreses(xApiKey, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  //public getAdres(id: number, xApiKey: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Adres>>;
  getAdres(id: number, xApiKey: string): Observable<HttpResponse<Adres>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.getAdres(id, xApiKey, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public getAdresses(page: number, size: number, sort?: Array<string>, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<PagedAdresses>>;
  getAdresses(xApiKey: string, page?: number, size?: number): Observable<HttpResponse<PagedAdresses>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.getAdresses(page!, size!,  xApiKey, ["id"], 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public patchAdres(id: number, xAPIKEY?: string, adres?: Adres, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Adres>>;
  patchAdres(id: number, xApiKey: string, adres?: Adres): Observable<HttpResponse<Adres>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.patchAdres(id, xApiKey, adres, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }


  //  public postAdres(override: boolean, adresBody: AdresBody, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Adres>>;
  postAdres(xApiKey: string, override: boolean, adresBody?: AdresBody): Observable<HttpResponse<Adres>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });
    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api!.postAdres(override, xApiKey, adresBody!, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public getAdresPerons(page: number, size: number, id: number, xAPIKEY: string, sort?: Array<string>, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<PagedPersons>>;
  getAdresPerons(xApiKey: string, id: number, page?: number, size?: number, sort?: Array<string>, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<PagedPersons>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.getAdresPerons(page!, size!, id, xApiKey, ["id"], 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }


}
