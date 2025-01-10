import { Injectable } from '@angular/core';
import { Role } from '../core/modules/openapi';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoleschangedService {
  constructor() { }

   private newRoleSubject = new Subject<Role>();
    newRole$ = this.newRoleSubject.asObservable();
  
    emitNewRole(role: Role) {
      this.newRoleSubject.next(role);
    }

}
