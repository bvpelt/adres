import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { Adres } from '../core/modules/openapi/model/adres';
import { AdresService } from '../services/adres.service';
import { LogonService } from '../services/logon.service';
import { AdresBody } from '../core/modules/openapi';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-adresdetail',
  templateUrl: './adresdetail.component.html',
  styleUrl: './adresdetail.component.css'
})
export class AdresdetailComponent {  
  adres?: Adres = undefined;
  errormessage?: string = undefined;

  user: string = '';
  password: string = '';

  isLoggedIn$: Observable<boolean>;
  username$: Observable<string>;
  password$: Observable<string>;

  constructor(private route: ActivatedRoute, 
    private adresService: AdresService, 
    private router: Router, 
    private logonService: LogonService) {
    this.getAdres(this.logonService.xApiKey);

    this.isLoggedIn$ = this.logonService.isLoggedIn$;

    this.username$ = this.logonService.username$;
    this.username$.subscribe({
      next:
        value => {
          this.user = value;
        },
      error:
        error => {
          console.log('AdresdetailComponent LogonService: ' + this.errormessage);
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
          console.log('AdresdetailComponent LogonService: ' + this.errormessage);
        }
    });
  }


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
    this.adresService.patchAdres(this.user, this.password, adres.id, this.logonService.xApiKey, adresbody)
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

  cancel() {
    console.log("cancel adres");
    this.router.navigate(['/adresses']);
  }
}
