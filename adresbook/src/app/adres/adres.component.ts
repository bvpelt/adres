import { Component } from '@angular/core';
import { Adres } from '../core/modules/openapi/model/adres';
import { AdresBody } from '../core/modules/openapi';
import { Router } from '@angular/router';
import { AdresService } from '../services/adres.service';
import { Observable } from 'rxjs';
import { LogonService } from '../services/logon.service';
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
    private adresService: AdresService,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private adresseschangedService: AdresseschangedService) {

    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }

  onSave(adres: Adres) {
    console.log('Add adres: ', adres);
    if (adres != {} as Adres) {
      this.dbgmessageService.debug('AdresComponent - Adding not empty adres');
      const adresbody: AdresBody = { street: adres.street, housenumber: adres.housenumber, zipcode: adres.zipcode, city: adres.city };
      this.adresService.postAdres(this.logonService.xApiKey, false, adres)
        .subscribe({
          next:
            response => {
              this.adres = response.body as Adres;
              this.adresseschangedService.emitNewAdres(adres);
              this.dbgmessageService.debug('AdresComponent - Emitted new adres');
              this.router.navigate(['/adresses']);
              this.dbgmessageService.debug('AdresComponent - Router navigate toe /adresses');
            },
          error: (error: HttpErrorResponse) => {
            this.errormessage = 'AdresComponent - Status: ' + error.status + ' details: ' + error.error.error + ' url: ' + error.url;
          }
        });
    } else {
      this.dbgmessageService.info('AdresComponent - Skip empty adres');
    }

  }

  cancel() {
    this.dbgmessageService.debug("AdresComponent - Cancel adres");
    this.router.navigate(['/adresses']);
  }
}
