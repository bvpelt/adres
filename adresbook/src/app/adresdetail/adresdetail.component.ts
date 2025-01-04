import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { Adres } from '../core/modules/openapi/model/adres';
import { AdresService } from '../services/adres.service';
import { LogonService } from '../services/logon.service';
import { AdresBody } from '../core/modules/openapi';
import { Observable } from 'rxjs';
import { OpenadresService } from '../services/openadres.service';
import { AdresseschangedService } from '../services/adresseschanged.service';
import { DbgmessageService } from '../services/dbgmessage.service';

@Component({
  selector: 'app-adresdetail',
  templateUrl: './adresdetail.component.html',
  styleUrl: './adresdetail.component.css'
})
export class AdresdetailComponent {
  adres?: Adres = undefined;
  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(private route: ActivatedRoute,
    //private adresService: AdresService,
    private openAdresService: OpenadresService,
    private router: Router,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private adresseschangedService: AdresseschangedService) {
    this.getAdres(this.logonService.xApiKey);

    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }

  /*
    getAdres(xApiKey: string): void {
      const id: number = Number(this.route.snapshot.paramMap.get('id'));
  
      this.adresService.getAdres(id, xApiKey)
        .subscribe({
          next:
            response => {
              if (response.body) {
                this.adres = response.body;
              }
            },
          error: error => {
            this.errormessage = 'AdresdetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
          }
        });
    }
  
  
    onUpdate(adres: Adres) {
      console.log("udpate adres");
  
      const adresbody: AdresBody = { street: adres.street, housenumber: adres.housenumber, zipcode: adres.zipcode, city: adres.city };
      this.adresService.patchAdres(this.logonService.authenticatedUser!, this.logonService.authenticatedPassword!, adres.id, this.logonService.xApiKey, adresbody)
        .subscribe({
          next:
            response => {
              this.adres = response.body as Adres;
            },
          error: error => {
            this.errormessage = 'AdresdetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
          }
        });
  
      this.router.navigate(['/adresses']);
    }
  */
  getAdres(xApiKey: string): void {
    const id: number = Number(this.route.snapshot.paramMap.get('id'));

    this.openAdresService.getAdres(id, xApiKey)
      .subscribe({
        next:
          response => {
            if (response.body) {
              this.adres = response.body;
            }
          },
        error: error => {
          this.errormessage = 'AdresdetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });
  }

  onUpdate(adres: Adres) {
    console.log("udpate adres");

    const adresbody: AdresBody = { street: adres.street, housenumber: adres.housenumber, zipcode: adres.zipcode, city: adres.city };
    this.openAdresService.patchAdres(adres.id, this.logonService.xApiKey, adresbody)
      .subscribe({
        next:
          response => {
            this.adres = response.body as Adres;
            this.adresseschangedService.emitNewAdres(adres);
            this.dbgmessageService.add('AdresdetailComponent - Emitted new adres');
            this.router.navigate(['/adresses']);
            this.dbgmessageService.add('AdresdetailComponent - Router navigate toe /adresses');
          },
        error: error => {
          this.errormessage = 'AdresdetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });

    this.router.navigate(['/adresses']);
  }

  cancel() {
    console.log("cancel adres");
    this.router.navigate(['/adresses']);
  }
}
