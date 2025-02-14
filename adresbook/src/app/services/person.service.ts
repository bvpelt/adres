import { Inject, Injectable, Optional } from '@angular/core';
import { BASE_PATH, Configuration, PagedPersons, Person, PersonBody, PersonsService } from '../core/modules/openapi';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { DynamicconfigService } from './dynamicconfig.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  private api: PersonsService | undefined;

  constructor(
    private http: HttpClient,
    @Optional() @Inject(BASE_PATH) basePath: string | string[],
    private dynamicConfigService: DynamicconfigService
  ) {
    var basePathToUse = Array.isArray(BASE_PATH) ? BASE_PATH[0] : BASE_PATH;
    var config: Configuration = new Configuration({
      basePath: basePathToUse
    });
    this.api = new PersonsService(http, basePath, config);
    this.dynamicConfigService.config$.subscribe((config: any) => {
      if (config) {
        this.api = new PersonsService(http, basePath, config);
      }
    });
  }

  // public deleteAllPersons(xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
  deleteAllPersons(xApiKey: string, page?: number, size?: number): Observable<HttpResponse<any>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers
    }

    if (this.api != undefined) {
      return this.api.deleteAllPersons(xApiKey, 'response', false, options);
    } else {
      throw new Error("PersonsService api not yet defined");
    }
  }

  // public deletePerson(id: number, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
  deletePerson(id: number, xApiKey: string): Observable<HttpResponse<any>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers
    }

    if (this.api != undefined) {
      return this.api.deletePerson(id, xApiKey, 'response', false, options);
    } else {
      throw new Error("PersonsService api not yet defined");
    }
  }

  // public getPerson(id: number, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Person>>;
  getPerson(id: number, xApiKey: string): Observable<HttpResponse<Person>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.getPerson(id, xApiKey, 'response', false, options);
    } else {
      throw new Error("PersonsService api not yet defined");
    }
  }

  // public getPersons(page: number, size: number, sort?: Array<string>, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<PagedPersons>>;
  getPersons(xApiKey: string, page?: number, size?: number): Observable<HttpResponse<PagedPersons>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.getPersons(page!, size!, xApiKey,  ["id"],'response', false, options);
    } else {
      throw new Error("PersonsService api not yet defined");
    }
  }

  // public patchPerson(id: number, xAPIKEY?: string, personBody?: PersonBody, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Person>>;
  patchPerson(id: number, xApiKey: string, personBody?: PersonBody): Observable<HttpResponse<Person>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.patchPerson(id, xApiKey, personBody, 'response', false, options);
    } else {
      throw new Error("PersonsService api not yet defined");
    }
  }

  // public postPerson(override: boolean, xAPIKEY?: string, personBody?: PersonBody, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Person>>;
  postPerson(xApiKey: string, override: boolean, personBody?: PersonBody): Observable<HttpResponse<Person>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });
    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api!.postPerson(override, xApiKey, personBody!, 'response', false, options);
    } else {
      throw new Error("PersonsService api not yet defined");
    }
  }

}
