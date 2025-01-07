import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Person } from '../core/modules/openapi';

@Injectable({
  providedIn: 'root'
})
export class PersonschangedService {

  constructor() { }
  private newPersonSubject = new Subject<Person>();
  newAdres$ = this.newPersonSubject.asObservable();

  emitNewPerson(person: Person) {
    this.newPersonSubject.next(person);
  }
}
