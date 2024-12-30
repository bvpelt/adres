import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Inject, Injectable, Optional } from '@angular/core';
import { DynamicconfigService } from './dynamicconfig.service';
import { Adres, AdresBody, AdressesService, BASE_PATH, Configuration } from '../core/modules/openapi';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OpenadresService {
  private api: AdressesService | undefined;

  constructor(
    private http: HttpClient,
    @Optional() @Inject(BASE_PATH) basePath: string | string[],
    private dynamicConfigService: DynamicconfigService
  ) {
    this.dynamicConfigService.config$.subscribe((config: any) => {
      if (config) {
        this.api = new AdressesService(http, basePath, config);
      }
    });
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
      return this.api!.postAdres(override, adresBody!, xApiKey, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }
}
