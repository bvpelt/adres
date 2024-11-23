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

import { Adres } from '../model/models';
import { AdresBody } from '../model/models';
import { AdresPerson } from '../model/models';
import { Adresses } from '../model/models';
import { ProblemDetail } from '../model/models';


import { Configuration }                                     from '../configuration';



export interface AdressesServiceInterface {
    defaultHeaders: HttpHeaders;
    configuration: Configuration;

    /**
     * Delete the specified adres
     * Delete only the specified adres 
     * @param id The identification of the resource to return
     * @param xApiKey An api key used to track usage of the api
     * @param authorization 
     */
    deleteAdres(id: number, xApiKey: string, authorization: string, extraHttpRequestParams?: any): Observable<{}>;

    /**
     * Delete all
     * Delete all adres 
     * @param xApiKey An api key used to track usage of the api
     * @param authorization 
     */
    deleteAllAdreses(xApiKey: string, authorization: string, extraHttpRequestParams?: any): Observable<{}>;

    /**
     * Retrieve the specified adres
     * Retrieve only one adres with id adresId 
     * @param id The identification of the resource to return
     * @param xApiKey An api key used to track usage of the api
     */
    getAdres(id: number, xApiKey: string, extraHttpRequestParams?: any): Observable<Adres>;

    /**
     * Retrieve the specified adres with references to all persons
     * Retrieve only one adres with id adresId and references to all persons on that adress 
     * @param id The identification of the resource to return
     * @param xApiKey An api key used to track usage of the api
     */
    getAdresPerons(id: number, xApiKey: string, extraHttpRequestParams?: any): Observable<AdresPerson>;

    /**
     * Retrieve all known adresses
     * Retrieve all known adresses, using paging and sorting 
     * @param xApiKey An api key used to track usage of the api
     * @param page pagenumber starts on 1
     * @param size number of elements on each page - minimum    1 - default   25 - maximum: 100 
     * @param sort field names to sort on
     */
    getAdresses(xApiKey: string, page?: number, size?: number, sort?: string, extraHttpRequestParams?: any): Observable<Adresses>;

    /**
     * Update the specified adres
     * Update only the adres specified with adresId 
     * @param id The identification of the resource to return
     * @param xApiKey An api key used to track usage of the api
     * @param authorization 
     * @param adresBody Request parameters
     */
    patchAdres(id: number, xApiKey: string, authorization: string, adresBody?: AdresBody, extraHttpRequestParams?: any): Observable<Adres>;

    /**
     * Create a new adres
     * Create a new adres To determine if an adres is new the input is compared on the hash value of: - street - housenumber - zipcode - city If the hash value already exists in the backend, the adres is considered to exist 
     * @param xApiKey An api key used to track usage of the api
     * @param authorization 
     * @param override parameter to override check on existence
     * @param adresBody Request parameters
     */
    postAdres(xApiKey: string, authorization: string, override?: boolean, adresBody?: AdresBody, extraHttpRequestParams?: any): Observable<Adres>;

}
