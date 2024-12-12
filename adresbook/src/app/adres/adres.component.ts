import { Component } from '@angular/core';
import { Adres } from '../core/modules/openapi/model/adres';
import { AdresBody } from '../core/modules/openapi';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { AdresService } from '../services/adres.service';
import { Observable } from 'rxjs';
import { LogonService } from '../services/logon.service';

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
    private location: Location,
    private logonService: LogonService) {

    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }

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

}
