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

import { Person } from '../model/models';
import { PersonAdres } from '../model/models';
import { PersonBody } from '../model/models';
import { Persons } from '../model/models';
import { ProblemDetail } from '../model/models';


import { Configuration }                                     from '../configuration';



export interface PersonsServiceInterface {
    defaultHeaders: HttpHeaders;
    configuration: Configuration;

    /**
     * Delete all persons
     * Delete all persons 
     * @param xApiKey An api key used to track usage of the api
     * @param authorization 
     */
    deleteAllPersons(xApiKey: string, authorization: string, extraHttpRequestParams?: any): Observable<{}>;

    /**
     * Delete the specified person
     * Delete only the specified person 
     * @param id The identification of the resource to return
     * @param xApiKey An api key used to track usage of the api
     * @param authorization 
     */
    deletePerson(id: number, xApiKey: string, authorization: string, extraHttpRequestParams?: any): Observable<{}>;

    /**
     * Retrieve the specified person with references to all adresses
     * Retrieve only one person with id personId and references to all adreses for that person 
     * @param id The identification of the resource to return
     * @param xApiKey An api key used to track usage of the api
     */
    getPeronsAdresses(id: number, xApiKey: string, extraHttpRequestParams?: any): Observable<PersonAdres>;

    /**
     * Retrieve the specified person
     * Retrieve only one person with id personId 
     * @param id The identification of the resource to return
     * @param xApiKey An api key used to track usage of the api
     */
    getPerson(id: number, xApiKey: string, extraHttpRequestParams?: any): Observable<Person>;

    /**
     * Retrieve all known persons
     * Retrieve all known persons, using paging and sorting 
     * @param xApiKey An api key used to track usage of the api
     * @param page pagenumber starts on 1
     * @param size number of elements on each page - minimum    1 - default   25 - maximum: 100 
     * @param sort field names to sort on
     */
    getPersons(xApiKey: string, page?: number, size?: number, sort?: string, extraHttpRequestParams?: any): Observable<Persons>;

    /**
     * Update the specified person
     * Update only the person specified with personId 
     * @param id The identification of the resource to return
     * @param xApiKey An api key used to track usage of the api
     * @param authorization 
     * @param personBody Request parameters
     */
    patchPerson(id: number, xApiKey: string, authorization: string, personBody?: PersonBody, extraHttpRequestParams?: any): Observable<Person>;

    /**
     * Create a new person
     * Create a new person To determine if an person is new the input is compared on the hash value of: - firstName - infix - lastName If the hash value already exists in the backend, the person is considered to exist 
     * @param xApiKey An api key used to track usage of the api
     * @param authorization 
     * @param override parameter to override check on existence
     * @param personBody Request parameters
     */
    postPerson(xApiKey: string, authorization: string, override?: boolean, personBody?: PersonBody, extraHttpRequestParams?: any): Observable<Person>;

}
