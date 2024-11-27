import { Component, OnInit } from '@angular/core';
import { Adres } from '../core/modules/openapi/model/adres';
import { Adresses } from '../core/modules/openapi/model/adresses';
import { AdresService } from '../services/adres.service';


@Component({
  selector: 'app-adreses',
  templateUrl: './adreses.component.html',
  styleUrl: './adreses.component.css'
})
export class AdresesComponent {
  //adres: Adres = { city: 'Veenendaal', housenumber: '12a', id: 1, street: 'Kerkewijk', zipcode: '3903 GA'};
  xApiKey: string = 'f0583805-03f6-4c7f-8e40-f83f55b7c077';
  page: number = 1;
  size: number = 10;
  adresList: Adresses = [];
  adres: Adres[] = [];
  user = 'bvpelt';
  password = '12345';
  errormessage: string = "";

  constructor(private adresService: AdresService) {
  }

  ngOnInit(): void {
    this.errormessage = "";
    this.getAdresses(this.user, this.password, this.xApiKey, this.page, this.size);
  }


  getAdresses(user: string, password: string, xApiKey: string, page: number, size: number): void {
    this.adresService.getAdresses(user, password, xApiKey, page, size)
      .subscribe({
        next:
          response => {
            if (response.body) {
              const adresses: Adres[] = response.body as Adres[];
              this.adres = adresses;
              console.log(response);
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
}
