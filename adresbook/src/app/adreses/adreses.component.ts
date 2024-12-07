import { Component, OnInit } from '@angular/core';
import { Adres } from '../core/modules/openapi/model/adres';
import { Adresses } from '../core/modules/openapi/model/adresses';
import { AdresService } from '../services/adres.service';
import { LogonService } from '../services/logon.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { faPencil, faTrashCan } from '@fortawesome/free-solid-svg-icons';


@Component({
  selector: 'app-adreses',
  templateUrl: './adreses.component.html',
  styleUrl: './adreses.component.css'
})
export class AdresesComponent {
  page: number = 1;
  size: number = 10;
  faPencilIcon = faPencil;
  faTrashCanIcon = faTrashCan;
  
  adres: Adres[] = [];
  selectedAdres?: Adres = undefined;
  
  errormessage?: string = undefined;
  user: string = '';
  password: string = '';

  username$: Observable<string>;
  password$: Observable<string>;


  isLoggedIn$: Observable<boolean>;

  constructor(private adresService: AdresService, 
    private router: Router, 
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
          console.log('AdresesComponent LogonService: ' + this.errormessage);
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
          console.log('AdresesComponent LogonService: ' + this.errormessage);
        }
    });
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
    this.router.navigate(['/adresdetail', adres.id]);    
  }

  onDelete(adres: Adres): void {
    console.log("Delete adres")
    this.selectedAdres = adres;

    this.adresService.deleteAdres(this.user, this.password, adres.id, this.logonService.xApiKey)
    .subscribe({
      next:
        response => {
          if (response != undefined) {
          console.log('AdresesComponent ' + JSON.stringify(response) );
        }
        },
      error: error => {
        this.errormessage = 'AdresesComponent Status: ' + error.status + ' details: ' + error.error.detail;
      }
    }

    )

    this.router.navigate(['/adresses']);
  }
}
