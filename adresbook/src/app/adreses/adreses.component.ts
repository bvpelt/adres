import { Component } from '@angular/core';
import { Adres } from '../core/modules/openapi/model/adres';

@Component({
  selector: 'app-adreses',
  templateUrl: './adreses.component.html',
  styleUrl: './adreses.component.css'
})
export class AdresesComponent {
  adres: Adres = { city: 'Veenendaal', housenumber: '12a', id: 1, street: 'Kerkewijk', zipcode: '3903 GA'};
}
