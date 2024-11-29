import { Component } from '@angular/core';
import { Adres } from '../core/modules/openapi/model/adres';
import { AdresBody } from '../core/modules/openapi';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { AdresService } from '../services/adres.service';

@Component({
  selector: 'app-adres',
  templateUrl: './adres.component.html',
  styleUrl: './adres.component.css'
})
export class AdresComponent {

  adres: Adres = {} as Adres; //{ city: 'Veenendaal', housenumber: '12a', id: 0, street: 'Kerkewijk', zipcode: '3903 GA'};
  xApiKey: string = 'f0583805-03f6-4c7f-8e40-f83f55b7c077';
  errormessage?: string = undefined;
  user: string = 'bvpelt';
  password: string = '12345';


  constructor(private route: ActivatedRoute, private adresService: AdresService, private location: Location) {
    // this.getAdres(this.xApiKey);
  }

  onSave(adres: Adres) {
    const adresbody: AdresBody = { street: adres.street, housenumber: adres.housenumber, zipcode: adres.zipcode, city: adres.city };
    this.adresService.postAdres(this.user, this.password, this.xApiKey, false, adresbody)
      .subscribe({
        next:
          response => {
            this.adres = response.body as Adres;
          },
        error: error => {
          this.errormessage = 'Status: ' + error.status + ' details: ' + error.error.detail;
        }
      })
  }

}
