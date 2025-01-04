import { Injectable } from '@angular/core';
import { LoginRequest, LoginResponse, LoginService, LoginTestResponse } from '../core/modules/openapi';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { DynamicconfigService } from './dynamicconfig.service';
import { DbgmessageService } from './dbgmessage.service';

@Injectable({
  providedIn: 'root'
})
export class LogonService {

  xApiKey: string = 'f0583805-03f6-4c7f-8e40-f83f55b7c077';
  loginResponse?: LoginResponse = undefined;
  errormessage?: string = undefined;

  authenticatedUser?: string = undefined;
  authenticatedPassword?: string = undefined;

  isLoggedIn = new BehaviorSubject<boolean>(false);

  isLoggedIn$ = this.isLoggedIn.asObservable();

  constructor(private api: LoginService,
    private dynamicconfigService: DynamicconfigService,
    private dbgmessageService: DbgmessageService) {
  }

  doLogOut() {
    this.isLoggedIn.next(false);
    this.authenticatedUser = undefined;
    this.authenticatedPassword = undefined;
  }

  doLogin(username: string, password: string) {
    this.postLogin(this.xApiKey, username, password)
      .subscribe({
        next:
          response => {
            if (response.body) {
              this.loginResponse = response.body;
              if (this.loginResponse.authenticated) {
                this.isLoggedIn.next(true);
                this.authenticatedUser = username;
                this.authenticatedPassword = password;
                this.dynamicconfigService.updateConfiguration(username, password, undefined);
                this.dbgmessageService.add('LogonService: authenticated');
              } else {
                this.isLoggedIn.next(false);
                this.authenticatedUser = undefined;
                this.authenticatedPassword = undefined;
                this.dbgmessageService.add('LogonService: not authenticated');
              }
            }
          },
        error: error => {
          this.errormessage = 'Status: ' + error.status + ' details: ' + error.error.detail;
          this.authenticatedUser = undefined;
          this.authenticatedPassword = undefined;
          this.dbgmessageService.add('LogonService: ' + this.errormessage);
        }
      });
  }

  doTestLogin(username: string, password: string): Observable<HttpResponse<LoginTestResponse>> {
    return this.postTestLogin(this.xApiKey, username, password);
    /*
      .subscribe({
        next:
          response => {
            if (response.body) {
              this.loginResponse = response.body;
              if (this.loginResponse.authenticated) {
                this.isLoggedIn.next(true);
                this.authenticatedUser = username;
                this.authenticatedPassword = password;
                if ((response.body.token != undefined) && (response.body.token.length > 0)) {
                  this.dynamicconfigService.updateConfiguration(username, password, response.body.token);
                } else {
                  this.dynamicconfigService.updateConfiguration(username, password, undefined);
                }
                this.dbgmessageService.add('LogonService: authenticated');
              } else {
                this.isLoggedIn.next(false);
                this.authenticatedUser = undefined;
                this.authenticatedPassword = undefined;
                this.dbgmessageService.add('LogonService: not authenticated');
              }
            }
          },
        error: error => {
          this.errormessage = 'Status: ' + error.status + ' details: ' + error.error.detail;
          this.authenticatedUser = undefined;
          this.authenticatedPassword = undefined;
          this.dbgmessageService.add('LogonService: ' + this.errormessage);
        }
      });
      */
  }

  /**
     * Login into the application
     * Login into the application by providing username password. 
     * @param xApiKey An api key used to track usage of the api
     * @param loginRequest Request parameters
     */
  //
  //public postLogin(loginRequest: LoginRequest, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<LoginResponse>>;
  private postLogin(xApiKey: string, username: string, password: string): Observable<HttpResponse<LoginResponse>> {
    const loginRequest: LoginRequest = { username: username, password: password };
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    };

    return this.api.postLogin(loginRequest, xApiKey, 'response', false, options);
  }

  //public postTestLogin(loginRequest: LoginRequest, xAPIKEY?: string, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<LoginTestResponse>>;
  private postTestLogin(xApiKey: string, username: string, password: string): Observable<HttpResponse<LoginTestResponse>> {
    const loginRequest: LoginRequest = { username: username, password: password };
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    };

    return this.api.postTestLogin(loginRequest, xApiKey, 'response', false, options);
  }

}
