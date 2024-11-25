import { Component, OnInit } from '@angular/core';
import { Adres } from '../core/modules/openapi/model/adres';
import { Adresses } from '../core/modules/openapi/model/adresses';
import { AdresService} from '../services/adres.service';


@Component({
  selector: 'app-adreses',
  templateUrl: './adreses.component.html',
  styleUrl: './adreses.component.css'
})
export class AdresesComponent {
  adres: Adres = { city: 'Veenendaal', housenumber: '12a', id: 1, street: 'Kerkewijk', zipcode: '3903 GA'};
  xApiKey: string = 'f0583805-03f6-4c7f-8e40-f83f55b7c077';
  page: number = 0;
  size: number = 10;
  adresList: Adresses = [];

  constructor(private adresService: AdresService) {
  }

  ngOnInit(): void {
    this.getAdresses();
  }

  getAdresses(): void {
    //this.adresList = this.getAdresses(this.xApiKey,0,0);
    this.adresService.getAdresses(this.xApiKey,0,0)
    .subscribe(adresList => this.adresList = adresList);
  }
}
