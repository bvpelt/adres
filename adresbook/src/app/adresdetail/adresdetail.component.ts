import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { Adres } from '../core/modules/openapi/model/adres';
import { AdresService } from '../services/adres.service';
import { LogonService } from '../services/logon.service';
import { AdresBody, Person } from '../core/modules/openapi';
import { BehaviorSubject, Observable } from 'rxjs';
import { AdresseschangedService } from '../services/adresseschanged.service';
import { DbgmessageService } from '../services/dbgmessage.service';
import { faPencil, faTrashCan } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-adresdetail',
  templateUrl: './adresdetail.component.html',
  styleUrl: './adresdetail.component.css'
})
export class AdresdetailComponent {
  adres?: Adres = undefined;
  errormessage?: string = undefined;
  selectedPerson?: Person = undefined;
  faPencilIcon = faPencil;
  faTrashCanIcon = faTrashCan;
  selectPerson: boolean = false;
//  private _selectPerson = new BehaviorSubject<boolean>(this.selectPerson);
//  selectPerson$ = this._selectPerson.asObservable();
  debugMessages: boolean = true;

  isLoggedIn$: Observable<boolean>;

  constructor(private route: ActivatedRoute,
    private adresService: AdresService,
    private router: Router,
    private location: Location,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private adresseschangedService: AdresseschangedService) {
    this.getAdres(this.logonService.xApiKey);

    this.isLoggedIn$ = this.logonService.isLoggedIn$;
//    this.selectPerson$.subscribe(value => {
//      this.selectPerson = value;      
//    });
  }


  getAdres(xApiKey: string): void {
    const id: number = Number(this.route.snapshot.paramMap.get('id'));

    this.adresService.getAdres(id, xApiKey)
      .subscribe({
        next:
          response => {
            if (response.body) {
              this.adres = response.body;
              console.log("AdresdetailComponent get adres: " + JSON.stringify(this.adres));
            }
          },
        error: error => {
          this.errormessage = 'AdresdetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });
  }

  onUpdate(adres: Adres) {
    const patchedAdres: Adres = { id: adres.id, street: adres.street, housenumber: adres.housenumber, zipcode: adres.zipcode, city: adres.city, persons: adres.persons };
    console.log("AdresdetailComponent update adres: " + JSON.stringify(this.adres));
    this.adresService.patchAdres(adres.id, this.logonService.xApiKey, patchedAdres)
      .subscribe({
        next:
          response => {
            this.adres = response.body as Adres;
            this.adresseschangedService.emitNewAdres(adres);
            this.dbgmessageService.debug('AdresdetailComponent - Emitted new adres');
            this.router.navigate(['/adresses']);
            this.dbgmessageService.debug('AdresdetailComponent - Router navigate toe /adresses');
          },
        error: error => {
          this.errormessage = 'AdresdetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });

    this.router.navigate(['/adresses']);
  }

  cancel() {
    this.location.back();
  }

  onSelectPerson(person: Person): void {
    this.selectedPerson = person;
    if (this.adres) {
      console.log("AdresdetailComponent add person: " + JSON.stringify(person));
      this.adres.persons?.push(person);
    }
  }

  onEditPerson(person: Person): void {
    this.selectedPerson = person;
    this.router.navigate(['/persondetail', person.id]);
  }


  onDeletePerson(person: Person): void {
    this.selectedPerson = person;
    if (this.adres) {
      console.log("AdresdetailComponent remove person: " + JSON.stringify(person));
      this.adres = this.removePersonFromAdres(this.adres, person);
    }
  }

  onAddPerson(): void {
    this.dbgmessageService.info("AdresdetailComponent toggled selectperson");    
   // this._selectPerson.next(!this._selectPerson.value);
   this.selectPerson = !this.selectPerson;
  }

  removePersonFromAdres(adres: Adres, personToRemove: Person): Adres {
    if (!adres || !adres.persons) {
      return adres; // Return the original adres if it's invalid or has no persons
    }
  
    return {
      ...adres, 
      persons: adres.persons.filter(person => person.id !== personToRemove.id) 
    };
  }
}
