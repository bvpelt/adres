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

import { LoginRequest } from '../model/models';
import { LoginResponse } from '../model/models';
import { ProblemDetail } from '../model/models';


import { Configuration }                                     from '../configuration';



export interface LoginServiceInterface {
    defaultHeaders: HttpHeaders;
    configuration: Configuration;

    /**
     * Login into the application
     * Login into the application by providing username password. 
     * @param xApiKey An api key used to track usage of the api
     * @param loginRequest Request parameters
     */
    postLogin(xApiKey: string, loginRequest?: LoginRequest, extraHttpRequestParams?: any): Observable<LoginResponse>;

}
