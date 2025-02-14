import { Inject, Injectable, Optional } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { RoleBody, BASE_PATH, RolesService, Role, PagedRoles, PrivilegesService, Privilege, PagedPrivileges, PrivilegeBody } from '../core/modules/openapi';
import { DynamicconfigService } from './dynamicconfig.service';


@Injectable({
  providedIn: 'root'
})
export class PrivilegeService {
private api: PrivilegesService | undefined;

  constructor(
    private http: HttpClient,
    @Optional() @Inject(BASE_PATH) basePath: string | string[],
    private dynamicConfigService: DynamicconfigService
  ) {
    this.dynamicConfigService.config$.subscribe((config: any) => {
      if (config) {
        this.api = new PrivilegesService(http, basePath, config);
      }
    });
  }


  // public deleteRole(id: number, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
  deletePrivilege(id: number, xApiKey: string): Observable<HttpResponse<any>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers
    }

    if (this.api != undefined) {
      return this.api.deletePrivilege(id, xApiKey, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public deleteAllRoles(xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<any>>;
  deleteAllPrivileges(xApiKey: string, page?: number, size?: number): Observable<HttpResponse<any>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers
    }

    if (this.api != undefined) {
      return this.api.deleteAllPrivileges(xApiKey, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public getRole(id: number, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Role>>;
  getPrivilege(id: number, xApiKey: string): Observable<HttpResponse<Privilege>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.getPrivilege(id, xApiKey, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public getRoles(page: number, size: number, sort?: Array<string>, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<PagedRoles>>;
  getPrivileges(xApiKey: string, page?: number, size?: number): Observable<HttpResponse<PagedPrivileges>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.getPrivileges(page!, size!, xApiKey,  ["id"], 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

  // public patchRole(id: number, xAPIKEY?: string, RoleBody?: RoleBody, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Role>>;
  patchPrivilege(id: number, xApiKey: string, privilegeBody?: PrivilegeBody): Observable<HttpResponse<Privilege>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api.patchPrivilege(id, xApiKey, privilegeBody, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }


  // public postRole(xAPIKEY?: string, RoleBody?: RoleBody, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<Role>>;
  postPrivilege(xApiKey: string, privilegeBody?: PrivilegeBody): Observable<HttpResponse<Privilege>> {
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });
    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    }

    if (this.api != undefined) {
      return this.api!.postPrivileges(xApiKey, privilegeBody!, 'response', false, options);
    } else {
      throw new Error("OpenadresService api not yet defined");
    }
  }

}
