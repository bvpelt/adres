import { Component, Input, OnInit } from '@angular/core';
import { Adres, PagedPersons, Person } from '../core/modules/openapi';
import { ActivatedRoute, Router } from '@angular/router';
import { AdresService } from '../services/adres.service';
import { LogonService } from '../services/logon.service';
import { DbgmessageService } from '../services/dbgmessage.service';

@Component({
  selector: 'app-personselect',
  templateUrl: './personselect.component.html',
  styleUrl: './personselect.component.css'
})
export class PersonselectComponent {

  @Input() adres?: Adres;
  nextpage: number = 0;
  prevpage: number = 0;
  totalpages: number = 0;
  page: number = 1;
  size: number = 4;
  persons: Person[] = [];
  errormessage?: string = undefined;
  selectedPerson?: Person = undefined;

  constructor(private route: ActivatedRoute,
    private adresService: AdresService,
    private router: Router,    
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService) {    
     
    }

    ngOnInit(): void {
      this.dbgmessageService.info("PersonselectComponent received adres: " + ((this.adres == undefined) ? "" : JSON.stringify(this.adres)));
      this.getPersons();
    }

    getPersons() {
      this.adresService.getAdresPerons(this.logonService.xApiKey, this.adres!.id, this.page, this.size, ["id"])
        .subscribe({
           next:
                   response => {
                     if (response.body) {
                       const personPage: PagedPersons = response.body;
                       //const persons: Person[] = response.body as Person[];
                       this.persons = personPage.content!;
                       this.dbgmessageService.trace('PersonselectComponent - before prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + personPage.totalPages);
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
                       this.dbgmessageService.trace('PersonselectComponent - after prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + personPage.totalPages);
                     }
                   },
         
                 error: error => {                  
                   this.errormessage = 'Status: ' + error.status + ' details: ' + error.error.detail;
                 }
        });
      
    }


  onNextPage(): void {
    this.page = this.nextpage;
    this.getPersons();
  }

  onPrevPage(): void {
    this.page = this.prevpage;
    this.getPersons();
  }


  onSelect(person: Person): void {
    this.selectedPerson = person;
    // update adres
    this.dbgmessageService.info("PersonselectComponent before: " + JSON.stringify(this.adres));
    this.adres?.persons?.push(person);
    this.dbgmessageService.info("PersonselectComponent after: " + JSON.stringify(this.adres));
  }
}
