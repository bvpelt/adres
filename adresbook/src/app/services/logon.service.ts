import { Injectable } from '@angular/core';
import { LoginRequest, LoginResponse, LoginService } from '../core/modules/openapi';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LogonService {

  xApiKey: string = 'f0583805-03f6-4c7f-8e40-f83f55b7c077';
  loginResponse?: LoginResponse = undefined;
  errormessage?: string = undefined;

  authenticatedUser?: string = undefined;
  authenticatedPassword?: string = undefined;

  private isLoggedIn = new BehaviorSubject<boolean>(false);

  isLoggedIn$ = this.isLoggedIn.asObservable();

  constructor(private api: LoginService) {
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
              } else {
                this.isLoggedIn.next(false);
                this.authenticatedUser = undefined;
                this.authenticatedPassword = undefined;
              }
            }
          },
        error: error => {
          this.errormessage = 'Status: ' + error.status + ' details: ' + error.error.detail;
          this.authenticatedUser = undefined;
          this.authenticatedPassword = undefined;
          console.log('LogonService: ' + this.errormessage);
        }
      });
  }

  /**
     * Login into the application
     * Login into the application by providing username password. 
     * @param xApiKey An api key used to track usage of the api
     * @param loginRequest Request parameters
     */
  //
  //public postLogin(xApiKey: string, loginRequest?: LoginRequest, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json' | 'application/problem+json', context?: HttpContext, transferCache?: boolean}): Observable<HttpResponse<LoginResponse>>;
  private postLogin(xApiKey: string, username: string, password: string): Observable<HttpResponse<LoginResponse>> {
    const loginRequest: LoginRequest = { username: username, password: password };
    const headers: HttpHeaders = new HttpHeaders({
      'x-api-key': xApiKey
    });

    const options: any = {
      headers: headers,
      httpHeaderAccept: 'application/json'
    };

    return this.api.postLogin(xApiKey, loginRequest, 'response', false, options);
  }
}
