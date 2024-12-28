import { Injectable, Inject, Optional } from '@angular/core';
import { Observable } from 'rxjs';
import { AdressesService } from '../core/modules/openapi/api/api';
import { Adresses } from '../core/modules/openapi/model/adresses';
import { Adres } from '../core/modules/openapi/model/adres';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AdresBody, BASE_PATH, Configuration } from '../core/modules/openapi';
import { LogonService } from './logon.service';
import { DbgmessageService } from './dbgmessage.service';

@Injectable({
  providedIn: 'root'
})
export class AdresService {

 

  api: AdressesService;
  
  /*
  constructor(private http: HttpClient, @Optional() @Inject(BASE_PATH) basePath: string | string[], private logonService: LogonService) {  
    const configuration = new Configuration({
      basePath: basePath, // Use the injected BASE_PATH
      username: this.logonService.authenticatedUser,
      password: this.logonService.authenticatedPassword
    });
    this.api = new AdressesService(http, basePath, configuration);
  }
*/
  constructor(
    private http: HttpClient, 
    @Optional() @Inject(BASE_PATH) basePath: string | string[],
    private logonService: LogonService,
    private dbgMessageService: DbgmessageService
  ) {
    const basePathToUse: string = Array.isArray(basePath) ? basePath[0] : basePath;

    dbgMessageService.add('AdresService constructor basePathToUse ' + basePathToUse);
    dbgMessageService.add('AdresService constructor user ' + this.logonService.authenticatedUser + ' password: ' + this.logonService.authenticatedPassword);
    
    const configuration = new Configuration({
      basePath: basePathToUse, // Use the injected BASE_PATH
      username: this.logonService.authenticatedUser,
      password: this.logonService.authenticatedPassword
    });

    dbgMessageService.add('AdresService constructor with configuration ' + JSON.stringify(configuration));
    this.api = new AdressesService(http, basePath, configuration); 
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

  // public getAdresses(xApiKey: string, page?: number, size?: number, sort?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Adresses>>;    
  getAdresses(xApiKey: string, page?: number, size?: number ):  Observable<HttpResponse<Adresses>> {    
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

  //  this.adresService.postAdres(this.logonService.authenticatedUser!, this.logonService.authenticatedPassword!, this.logonService.xApiKey, false, adresbody)

  //  public postAdres(override: boolean, adresBody: AdresBody, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Adres>>;
  postAdres(user: string, password: string, xApiKey: string, override: boolean, adresBody?: AdresBody ):  Observable<HttpResponse<Adres>> {
    const credentials: string = user + ':' + password;
    const authorization: string = 'Basic ' + btoa(credentials);
    const headers: HttpHeaders = new HttpHeaders({
//      'Authorization': authorization,
      'x-api-key' : xApiKey  
    });
    const options:any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    // Convert Headers to an array of key-value pairs
    // Authorization: Basic YnZwZWx0OjEyMzQ1
    // Authorization: Basic YnZwZWx0OjEyMzQ1
    /*
    const headersArray = Array.from(headers.keys()).map(key => ({ 
      key, 
      value: headers.get(key) 
    }));

    console.log('Adresservie user: ' + user + ' password: ' + password + ' postAdres headers: ', headers);
    console.log(headersArray);
    console.log('Adresservie adresbody: ' + JSON.stringify(adresBody));
*/
    this.dbgMessageService.add('postAdres');
    return this.api.postAdres(override, adresBody!, xApiKey, 'response', false, options);
  }

}
