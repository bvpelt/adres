import { Inject, Injectable, Optional } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { BASE_PATH, Configuration } from '../core/modules/openapi';
import { DbgmessageService } from './dbgmessage.service';

@Injectable({
  providedIn: 'root'
})
export class DynamicconfigService {

  private _basePathToUse: string;

  /*
  constructor(private dbgmessageService: DbgmessageService, @Optional() @Inject(BASE_PATH) basePath: string | string[]) {
    this._basePathToUse = Array.isArray(basePath) ? basePath[0] : basePath;
  }
    */

  constructor(private dbgmessageService: DbgmessageService, private configuration: Configuration) {
    if (this.configuration.basePath != undefined) {
      this._basePathToUse = this.configuration.basePath;
    } else {
      this._basePathToUse = 'http://localhost:8080/adres/api/v1';
    }
  }
  
/*
  get basePathToUse(): string {
    if (!this._basePathToUse) {
      this._basePathToUse = Array.isArray(BASE_PATH) ? BASE_PATH[0] : BASE_PATH;
    }
    return this._basePathToUse;
  }
    */

  private configSubject = new BehaviorSubject<Configuration | null>(null);
  config$ = this.configSubject.asObservable();

  updateConfiguration(username: string, password: string, token: string | undefined) {
    this.dbgmessageService.debug('DynamicconfigService username: ' + username + ' password: ' + password + ' token: ' + token);
    const newConfig = (token != undefined) ?
      new Configuration({
        basePath: this._basePathToUse,
        accessToken: token
      }) :
      new Configuration({
        basePath: this._basePathToUse,
        username,
        password
      });
    this.dbgmessageService.debug('DynamicconfigService generated config: ' + JSON.stringify(newConfig));
    this.configSubject.next(newConfig);
  }

}
