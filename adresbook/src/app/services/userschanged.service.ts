import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { User } from '../core/modules/openapi';


@Injectable({
  providedIn: 'root'
})
export class UserschangedService {

  constructor() { }

   private newUserSubject = new Subject<User>();
    newUser$ = this.newUserSubject.asObservable();
  
    emitNewUser(user: User) {
      this.newUserSubject.next(user);
    }
}
