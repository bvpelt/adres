import { Component } from '@angular/core';
import { Person, PersonBody } from '../core/modules/openapi';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { PersonService } from '../services/person.service';
import { LogonService } from '../services/logon.service';
import { DbgmessageService } from '../services/dbgmessage.service';
import { PersonschangedService } from '../services/personschanged.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrl: './person.component.css'
})
export class PersonComponent {
  person: Person = {} as Person;
  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(private router: Router,
    private personService: PersonService,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private personschangedService: PersonschangedService) {

    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }

  onSave(person: Person) {
    console.log('Add person: ', person);
    if (person != {} as Person) {
      this.dbgmessageService.add('PersonComponent - Adding not empty person');
      const personbody: PersonBody = { firstName: person.firstName, infix: person.infix, lastName: person.lastName, dateOfBirth: person.dateOfBirth };
      this.personService.postPerson(this.logonService.xApiKey, false, person)
        .subscribe({
          next:
            response => {
              this.person = response.body as Person;
              this.personschangedService.emitNewPerson(person);
              this.dbgmessageService.debug('PersonComponent - Emitted new person');
              this.router.navigate(['/persons']);
              this.dbgmessageService.debug('PersonComponent - Router navigate toe /persons');
            },
          error: (error: HttpErrorResponse) => {
            this.errormessage = 'PersonComponent - Status: ' + error.status + ' details: ' + error.error.error + ' url: ' + error.url;
          }
        });
    } else {
      this.dbgmessageService.debug('PersonComponent - Skip empty person');
    }
  }

  cancel() {
    this.dbgmessageService.debug("AdresComponent - Cancel person");
    this.router.navigate(['/persons']);
  }
}
