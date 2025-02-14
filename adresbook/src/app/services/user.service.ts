import { Inject, Injectable, Optional } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { UserBody, BASE_PATH, UsersService, User, PagedUsers } from '../core/modules/openapi';
import { DynamicconfigService } from './dynamicconfig.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private api: UsersService | undefined;

  constructor(
    private http: HttpClient,
    @Optional() @Inject(BASE_PATH) basePath: string | string[],
    private dynamicConfigService: DynamicconfigService
  ) {
    this.dynamicConfigService.config$.subscribe((config: any) => {
      if (config) {
        this.api = new UsersService(http, basePath, config);
      }
    });
  }

  // public deleteUser(id: number, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
  deleteUser(id: number, xApiKey: string): Observable<HttpResponse<any>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers
    }

    if (this.api != undefined) {
      return this.api.deleteUser(id, xApiKey, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public deleteAllUsers(xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
  deleteAllUsers(xApiKey: string, page?: number, size?: number): Observable<HttpResponse<any>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers
    }

    if (this.api != undefined) {
      return this.api.deleteAllUsers(xApiKey, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public getUser(id: number, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<User>>;
  getUser(id: number, xApiKey: string): Observable<HttpResponse<User>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.getUser(id, xApiKey, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public getUsers(page: number, size: number, sort?: Array<string>, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<PagedUsers>>;
  getUsers(xApiKey: string, page?: number, size?: number): Observable<HttpResponse<PagedUsers>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.getUsers(page!, size!, xApiKey, ["id"], 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public patchUser(id: number, xAPIKEY?: string, userBody?: UserBody, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<User>>;
  patchUser(id: number, xApiKey: string, adresBody?: UserBody): Observable<HttpResponse<User>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.patchUser(id, xApiKey, adresBody, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }


  // public postUser(xAPIKEY?: string, userBody?: UserBody, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<User>>;
  postUser(xApiKey: string, userBody?: UserBody): Observable<HttpResponse<User>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });
    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api!.postUser(xApiKey, userBody!, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }
}
