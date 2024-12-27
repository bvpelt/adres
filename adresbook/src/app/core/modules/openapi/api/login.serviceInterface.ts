/**
 * Adres API
 *
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { HttpHeaders }                                       from '@angular/common/http';

import { Observable }                                        from 'rxjs';

import { AuthenticateRequest } from '../model/models';
import { AuthenticateResponse } from '../model/models';
import { LoginRequest } from '../model/models';
import { LoginResponse } from '../model/models';
import { ProblemDetail } from '../model/models';
import { RegisterRequest } from '../model/models';


import { Configuration }                                     from '../configuration';



export interface LoginServiceInterface {
    defaultHeaders: HttpHeaders;
    configuration: Configuration;

    /**
     * Authenticate a user to get access to the resources
     * Authenticate a user and get a token which must be uses to access the resources 
     * @param xAPIKEY An api key used to track usage of the api
     * @param authenticateRequest Request parameters
     */
    postAuthenticate(xAPIKEY?: string, authenticateRequest?: AuthenticateRequest, extraHttpRequestParams?: any): Observable<AuthenticateResponse>;

    /**
     * Login into the application
     * Login into the application by providing username password. 
     * @param loginRequest Request parameters
     * @param xAPIKEY An api key used to track usage of the api
     */
    postLogin(loginRequest: LoginRequest, xAPIKEY?: string, extraHttpRequestParams?: any): Observable<LoginResponse>;

    /**
     * Register for the application
     * Register for the application by providing username password. A JWT token will be returned an must be used as token for other calls 
     * @param xAPIKEY An api key used to track usage of the api
     * @param registerRequest Request parameters
     */
    postRegister(xAPIKEY?: string, registerRequest?: RegisterRequest, extraHttpRequestParams?: any): Observable<AuthenticateResponse>;

}
