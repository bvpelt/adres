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

  adres: Adres = {} as Adres; //{ city: 'Veenendaal', housenumber: '12a', id: 0, street: 'Kerkewijk', zipcode: '3903 GA'};
  //xApiKey: string = 'f0583805-03f6-4c7f-8e40-f83f55b7c077';
  errormessage?: string = undefined;
  user: string = '';
  password: string = '';

  isLoggedIn$: Observable<boolean>;
  username$: Observable<string>;
  password$: Observable<string>;

  constructor(private router: Router,
    private adresService: AdresService,
    private location: Location,
    private logonService: LogonService) {

    this.isLoggedIn$ = this.logonService.isLoggedIn$;

    this.username$ = this.logonService.username$;
    this.username$.subscribe({
      next:
        value => {
          this.user = value;
        },
      error:
        error => {
          console.log('AdresComponent LogonService: ' + this.errormessage);
        }
    });

    this.password$ = this.logonService.password$;
    this.password$.subscribe({
      next:
        value => {
          this.password = value;
        },
      error:
        error => {
          this.errormessage = 'Status: ' + error.status + ' details: ' + error.error.detail;
          console.log('AdresComponent LogonService: ' + this.errormessage);
        }
    });
  }

  onSave(adres: Adres) {
    console.log('Add adres: ', adres);
    if (adres != {} as Adres) {
      console.log('Adding not empty adres');
      const adresbody: AdresBody = { street: adres.street, housenumber: adres.housenumber, zipcode: adres.zipcode, city: adres.city };
      this.adresService.postAdres(this.user, this.password, this.logonService.xApiKey, false, adresbody)
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
