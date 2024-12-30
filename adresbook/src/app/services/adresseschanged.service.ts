import { Injectable } from '@angular/core';
import { Adres } from '../core/modules/openapi';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdresseschangedService {

  constructor() { }

  private newAdresSubject = new Subject<Adres>();
  newAdres$ = this.newAdresSubject.asObservable();

  emitNewAdres(adres: Adres) {
    this.newAdresSubject.next(adres);
  }
}
