import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { PersonService } from '../services/person.service';
import { LogonService } from '../services/logon.service';
import { DbgmessageService } from '../services/dbgmessage.service';
import { PersonschangedService } from '../services/personschanged.service';
import { Observable } from 'rxjs';
import { Person, PersonBody } from '../core/modules/openapi';

@Component({
  selector: 'app-persondetail',
  templateUrl: './persondetail.component.html',
  styleUrl: './persondetail.component.css'
})
export class PersondetailComponent {
  person?: Person = undefined;
  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(private route: ActivatedRoute,
    private personService: PersonService,
    private router: Router,
    private location: Location,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private personschangedService: PersonschangedService) {
    this.getAdres(this.logonService.xApiKey);

    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }

  getAdres(xApiKey: string): void {
    const id: number = Number(this.route.snapshot.paramMap.get('id'));

    this.personService.getPerson(id, xApiKey)
      .subscribe({
        next:
          response => {
            if (response.body) {
              this.person = response.body;
            }
          },
        error: error => {
          this.errormessage = 'PersondetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });
  }

  onUpdate(person: Person) {
    const personbody: PersonBody = { firstName: person.firstName, infix: person.infix, lastName: person.lastName, dateOfBirth: person.dateOfBirth };
    this.personService.patchPerson(person.id, this.logonService.xApiKey, personbody)
      .subscribe({
        next:
          response => {
            this.person = response.body as Person;
            this.personschangedService.emitNewPerson(person);
            this.dbgmessageService.debug('PersondetailComponent - Emitted new person');
            this.router.navigate(['/persons']);
            this.dbgmessageService.debug('PersondetailComponent - Router navigate toe /persons');
          },
        error: error => {
          this.errormessage = 'PersondetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });

    this.router.navigate(['/persons']);
  }

  cancel() {
    this.location.back();
  }
}
