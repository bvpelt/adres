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

import { ProblemDetail } from '../model/models';
import { Role } from '../model/models';
import { RoleBody } from '../model/models';
import { Roles } from '../model/models';


import { Configuration }                                     from '../configuration';



export interface RolesServiceInterface {
    defaultHeaders: HttpHeaders;
    configuration: Configuration;

    /**
     * Delete all
     * Delete all roles 
     * @param xApiKey An api key used to track usage of the api
     * @param authorization 
     */
    deleteAllRoles(xApiKey: string, authorization: string, extraHttpRequestParams?: any): Observable<{}>;

    /**
     * Delete the specified role
     * Delete only the specified role 
     * @param id The identification of the resource to return
     * @param xApiKey An api key used to track usage of the api
     * @param authorization 
     */
    deleteRole(id: number, xApiKey: string, authorization: string, extraHttpRequestParams?: any): Observable<{}>;

    /**
     * Retrieve the specified role
     * Retrieve only one role with id roleId 
     * @param id The identification of the resource to return
     * @param xApiKey An api key used to track usage of the api
     */
    getRole(id: number, xApiKey: string, extraHttpRequestParams?: any): Observable<Role>;

    /**
     * Retrieve all known roles
     * Retrieve all known roles, using paging and sorting 
     * @param xApiKey An api key used to track usage of the api
     * @param page pagenumber starts on 1
     * @param size number of elements on each page - minimum    1 - default   25 - maximum: 100 
     * @param sort field names to sort on
     */
    getRoles(xApiKey: string, page?: number, size?: number, sort?: string, extraHttpRequestParams?: any): Observable<Roles>;

    /**
     * Update the specified role
     * Update only the role specified with roleId 
     * @param id The identification of the resource to return
     * @param xApiKey An api key used to track usage of the api
     * @param authorization 
     * @param roleBody Request parameters
     */
    patchRole(id: number, xApiKey: string, authorization: string, roleBody?: RoleBody, extraHttpRequestParams?: any): Observable<Role>;

    /**
     * Create a new role
     * Create a new role To determine if an role is new the input is compared on the hash value of: - rolename - description If the hash value already exists in the backend, the role is considered to exist 
     * @param xApiKey An api key used to track usage of the api
     * @param authorization 
     * @param roleBody Request parameters
     */
    postRole(xApiKey: string, authorization: string, roleBody?: RoleBody, extraHttpRequestParams?: any): Observable<Role>;

}