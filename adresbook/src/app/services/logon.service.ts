import { Inject, Injectable, Optional } from '@angular/core';
import { BASE_PATH, Configuration, LoginRequest, LoginResponse, LoginService } from '../core/modules/openapi';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { DynamicconfigService } from './dynamicconfig.service';
import { DbgmessageService } from './dbgmessage.service';

@Injectable({
  providedIn: 'root'
})
export class LogonService {

  xApiKey: string = 'f0583805-03f6-4c7f-8e40-f83f55b7c077';
  private api: LoginService | undefined;
  loginResponse?: LoginResponse = undefined;
  errormessage?: string = undefined;

  authenticatedUser?: string = undefined;
  authenticatedPassword?: string = undefined;

  isLoggedIn = new BehaviorSubject<boolean>(false);

  isLoggedIn$ = this.isLoggedIn.asObservable();

  basePathToUse = Array.isArray(BASE_PATH) ? BASE_PATH[0] : BASE_PATH;

  constructor(
    private http: HttpClient,
    private dynamicconfigService: DynamicconfigService,
    private dbgmessageService: DbgmessageService,
    @Optional() @Inject(BASE_PATH) basePath: string | string[]
  ) {
    this.basePathToUse = Array.isArray(BASE_PATH) ? BASE_PATH[0] : BASE_PATH;
    var config: Configuration = new Configuration({
      basePath: this.basePathToUse
    });
    this.isLoggedIn.next(false);
    this.authenticatedUser = undefined;
    this.authenticatedPassword = undefined;
    this.api = new LoginService(http, basePath, config);
  }
 
  doLogOut() {
    this.isLoggedIn.next(false);
    this.authenticatedUser = undefined;
    this.authenticatedPassword = undefined;
  }

  doLogin(username: string, password: string, email: string): Observable<HttpResponse<LoginResponse>> {
    return this.postLogin(this.xApiKey, username, password, email);
  }

  //public postLogin(loginRequest: LoginRequest, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<LoginResponse>>;
  private postLogin(xApiKey: string, username: string, password: string, email: string): Observable<HttpResponse<LoginResponse>> {
    const loginRequest: LoginRequest = { username: username, password: password, email: email };
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    };

    if (this.api != undefined) {
      return this.api.postLogin( xApiKey, loginRequest, 'response', false, options);
    } else {
      console.log("LogonService no api available")
      throw Error("No api");
    }
  }

}
