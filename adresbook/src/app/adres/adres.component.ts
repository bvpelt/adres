import { Component } from '@angular/core';
import { Adres } from '../core/modules/openapi/model/adres';
import { AdresBody } from '../core/modules/openapi';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { AdresService } from '../services/adres.service';
import { Observable } from 'rxjs';
import { LogonService } from '../services/logon.service';
import { OpenadresService } from '../services/openadres.service';
import { DbgmessageService } from '../services/dbgmessage.service';
import { AdresseschangedService } from '../services/adresseschanged.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-adres',
  templateUrl: './adres.component.html',
  styleUrl: './adres.component.css'
})
export class AdresComponent {

  adres: Adres = {} as Adres;
  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(private router: Router,
    //private adresService: AdresService,
    private openAdresService: OpenadresService,
    private location: Location,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private adresseschangedService: AdresseschangedService) {

    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }

  /*
  onSave(adres: Adres) {
    console.log('Add adres: ', adres);
    if (adres != {} as Adres) {
      console.log('Adding not empty adres');
      const adresbody: AdresBody = { street: adres.street, housenumber: adres.housenumber, zipcode: adres.zipcode, city: adres.city };
      this.adresService.postAdres(this.logonService.authenticatedUser!, this.logonService.authenticatedPassword!, this.logonService.xApiKey, false, adresbody)
        .subscribe({
          next:
            response => {
              this.adres = response.body as Adres;
            },
          error: error => {
            this.errormessage = 'AdresComponent Status: ' + error.status + ' details: ' + error.error.detail;
          }
        });
    } else {
      console.log('Skip empty adres');
    }
    this.router.navigate(['/adresses']);
  }
*/

  onSave(adres: Adres) {
    console.log('Add adres: ', adres);
    if (adres != {} as Adres) {
      this.dbgmessageService.add('AdresComponent - Adding not empty adres');
      const adresbody: AdresBody = { street: adres.street, housenumber: adres.housenumber, zipcode: adres.zipcode, city: adres.city };
      this.openAdresService.postAdres(this.logonService.xApiKey, false, adres)
        .subscribe({
          next:
            response => {
              this.adres = response.body as Adres;
              this.adresseschangedService.emitNewAdres(adres);
              this.dbgmessageService.add('AdresComponent - Emitted new adres');
              this.router.navigate(['/adresses']);
              this.dbgmessageService.add('AdresComponent - Router navigate toe /adresses');
            },

            /*
            AdresComponent - Status: 403 details: Forbidden message: Http failure response for http://localhost:8080/adres/api/v1/adresses?Override=false: 403 OK url: /adres/api/v1/adresses {"headers":{"normalizedNames":{},"lazyUpdate":null},"status":403,"statusText":"OK","url":"http://localhost:8080/adres/api/v1/adresses?Override=false","ok":false,"name":"HttpErrorResponse","message":"Http failure response for http://localhost:8080/adres/api/v1/adresses?Override=false: 403 OK","error":{"timestamp":"2025-01-04T17:38:48.450+00:00","status":403,"error":"Forbidden","message":"Forbidden","path":"/adres/api/v1/adresses"}}
            */
          error: (error: HttpErrorResponse) => {
            this.errormessage = 'AdresComponent - Status: ' + error.status + ' details: ' + error.error.error + ' url: ' + error.url;
            console.log(error);
          }
        });
    } else {
      this.dbgmessageService.add('AdresComponent - Skip empty adres');
    }
   
  }

  cancel() {
    this.dbgmessageService.add("AdresComponent - Cancel adres");
    this.router.navigate(['/adresses']);
  }
}
