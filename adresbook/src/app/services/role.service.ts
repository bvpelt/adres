import { Inject, Injectable, Optional } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { RoleBody, BASE_PATH, RolesService, Role, PagedRoles } from '../core/modules/openapi';
import { DynamicconfigService } from './dynamicconfig.service';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private api: RolesService | undefined;

  constructor(
    private http: HttpClient,
    @Optional() @Inject(BASE_PATH) basePath: string | string[],
    private dynamicConfigService: DynamicconfigService
  ) {
    this.dynamicConfigService.config$.subscribe((config: any) => {
      if (config) {
        this.api = new RolesService(http, basePath, config);
      }
    });
  }


  // public deleteRole(id: number, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
  deleteRole(id: number, xApiKey: string): Observable<HttpResponse<any>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers
    }

    if (this.api != undefined) {
      return this.api.deleteRole(id, xApiKey, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public deleteAllRoles(xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
  deleteAllRoles(xApiKey: string, page?: number, size?: number): Observable<HttpResponse<any>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers
    }

    if (this.api != undefined) {
      return this.api.deleteAllRoles(xApiKey, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public getRole(id: number, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Role>>;
  getRole(id: number, xApiKey: string): Observable<HttpResponse<Role>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.getRole(id, xApiKey, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public getRoles(page: number, size: number, sort?: Array<string>, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<PagedRoles>>;
  getRoles(xApiKey: string, page?: number, size?: number): Observable<HttpResponse<PagedRoles>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.getRoles(page!, size!, xApiKey,  ["id"], 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public patchRole(id: number, xAPIKEY?: string, RoleBody?: RoleBody, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Role>>;
  patchRole(id: number, xApiKey: string, roleBody?: RoleBody): Observable<HttpResponse<Role>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.patchRole(id, xApiKey, roleBody, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }


  // public postRole(xAPIKEY?: string, RoleBody?: RoleBody, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Role>>;
  postRole(xApiKey: string, roleBody?: RoleBody): Observable<HttpResponse<Role>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });
    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api!.postRole(xApiKey, roleBody!, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

}
