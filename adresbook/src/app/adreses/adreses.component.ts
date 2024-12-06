import { Component, OnInit } from '@angular/core';
import { Adres } from '../core/modules/openapi/model/adres';
import { Adresses } from '../core/modules/openapi/model/adresses';
import { AdresService } from '../services/adres.service';
import { LogonService } from '../services/logon.service';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-adreses',
  templateUrl: './adreses.component.html',
  styleUrl: './adreses.component.css'
})
export class AdresesComponent {
  page: number = 1;
  size: number = 10;
  
  adres: Adres[] = [];
  selectedAdres?: Adres = undefined;
  
  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(private adresService: AdresService, private logonService: LogonService) {
    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }

  ngOnInit(): void {
    this.errormessage = "";
    this.getAdresses(this.logonService.xApiKey, this.page, this.size);    
  }

  getAdresses(xApiKey: string, page: number, size: number): void {    
    this.adresService.getAdresses(xApiKey, page, size)
      .subscribe({
        next:
          response => {
            if (response.body) {
              const adresses: Adres[] = response.body as Adres[];
              this.adres = adresses;              
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

  onSelect(adres: Adres): void {
    this.selectedAdres = adres;
  }
}
