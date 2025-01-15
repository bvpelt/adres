import { Injectable } from '@angular/core';
import { Privilege } from '../core/modules/openapi';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PrivilegechangedService {

  constructor() { }

     private newPrivilegeSubject = new Subject<Privilege>();
      newPrivilege$ = this.newPrivilegeSubject.asObservable();
    
      emitNewPrivilege(privilege: Privilege) {
        this.newPrivilegeSubject.next(privilege);
      }
  
}
