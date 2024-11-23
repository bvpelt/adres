import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdressesService } from '../core/modules/openapi';
import { Adres } from '../core/modules/openapi';

@Injectable({
  providedIn: 'root'
})
export class AdresService {

  constructor(private api: AdresService) { }

  getAdresses(): Observable<Adres[]> {
    return this.api.getAdresses();
  }
}
