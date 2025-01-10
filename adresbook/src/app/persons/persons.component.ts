import { Component } from '@angular/core';
import { faPencil, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { PagedPersons, Person } from '../core/modules/openapi';
import { Observable, Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { LogonService } from '../services/logon.service';
import { DbgmessageService } from '../services/dbgmessage.service';
import { PersonschangedService } from '../services/personschanged.service';
import { PersonService } from '../services/person.service';

@Component({
  selector: 'app-persons',
  templateUrl: './persons.component.html',
  styleUrl: './persons.component.css'
})
export class PersonsComponent {

  page: number = 1;
  size: number = 4;
  faPencilIcon = faPencil;
  faTrashCanIcon = faTrashCan;

  persons: Person[] = [];
  nextpage: number = 0;
  prevpage: number = 0;
  totalpages: number = 0;
  selectedPerson?: Person = undefined;
  personChangesubscription: Subscription | undefined;

  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(
    private personService: PersonService,
    private router: Router,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private personschangedService: PersonschangedService) {
    this.dbgmessageService.debug('PersonsComponent - constructed subscription defined');
    this.isLoggedIn$ = this.logonService.isLoggedIn$;
    this.personChangesubscription = this.personschangedService.newAdres$
      .subscribe(adres => {
        this.dbgmessageService.debug('PersonsComponent - retrieve adresses go add: ' + JSON.stringify(adres));
        this.getPersons(this.logonService.xApiKey, this.page, this.size);
      });
  }

  ngOnInit(): void {
    this.dbgmessageService.debug('PersonsComponent - activated initial');
    this.errormessage = "";
    this.getPersons(this.logonService.xApiKey, this.page, this.size);    
  }

  getPersons(xApiKey: string, page: number, size: number): void {
    this.personService.getPersons(xApiKey, page, size)
      .subscribe({
        next:
          response => {
            if (response.body) {
              const personPage: PagedPersons = response.body;
              const adresses: Person[] = response.body as Person[];
              this.persons = personPage.content!;
              this.dbgmessageService.trace('PersonsComponent - before prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + personPage.totalPages);
              if (personPage.totalElements != undefined) {
                this.totalpages = personPage.totalPages!;
                if (this.page + 1 <= personPage.totalPages!) {
                  this.nextpage = this.page + 1;
                } else {
                  this.nextpage = 0;
                }
                if (this.page - 1 > 0) {
                  this.prevpage = this.page - 1;
                } else {
                  this.prevpage = 0;
                }
              }
              this.dbgmessageService.trace('PersonsComponent - after prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + personPage.totalPages);
            }
          },

        error: error => {
          /*
          console.log('error: ' + JSON.stringify(error));
          console.log('error message: ' + error.message);
          console.log('error status: ' + error.status);
          console.log('error error: ' + JSON.stringify(error.error));
          console.log('error detail: ' + error.error.detail);
          */
          this.errormessage = 'Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });
  }

  onSelect(person: Person): void {
    this.selectedPerson = person;
    this.router.navigate(['/persondetail', person.id]);
  }

  onNextPage(): void {
    this.page = this.nextpage;
    this.getPersons(this.logonService.xApiKey, this.page, this.size);
  }

  onPrevPage(): void {
    this.page = this.prevpage;
    this.getPersons(this.logonService.xApiKey, this.page, this.size);
  }

  onDelete(person: Person): void {
    console.log("Delete person")
    this.selectedPerson = person;

    this.personService.deletePerson(person.id, this.logonService.xApiKey)
      .subscribe({
        next:
          response => {
            this.dbgmessageService.debug('PersonsComponent - deleted status: ' + response.status);
            this.personschangedService.emitNewPerson(person);
          },

        error: error => {
          this.errormessage = 'PersonsComponent - Status: ' + error.status + ' details: ' + error.error.detail;
        }
      }
      )

    this.router.navigate(['/persons']);
  }

  ngOnDestroy() {

    if (this.personChangesubscription) {
      this.personChangesubscription.unsubscribe();
      this.dbgmessageService.debug('PersonsComponent - Subscription destroyed');
    }

    this.dbgmessageService.debug('PersonsComponent - Destroyed');
  }

}
