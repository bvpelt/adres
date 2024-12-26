import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
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
export class AdresesComponent implements OnInit, OnChanges {
  @Input() isFirstActivation: boolean = true;
  
  page: number = 1;
  size: number = 10;
  faPencilIcon = faPencil;
  faTrashCanIcon = faTrashCan;

  adres: Adres[] = [];
  selectedAdres?: Adres = undefined;

  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(private adresService: AdresService,
    private router: Router,
    private logonService: LogonService) {
    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }

  ngOnInit(): void {
    this.errormessage = "";
    this.getAdresses(this.logonService.xApiKey, this.page, this.size);
    console.log('Component activated initial');
    this.isFirstActivation = false;
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['isFirstActivation'] && !changes['isFirstActivation'].firstChange) {
      // Code to execute on subsequent activations
      this.getAdresses(this.logonService.xApiKey, this.page, this.size);
      console.log('Component activated again');
    }
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

    this.adresService.deleteAdres(this.logonService.authenticatedUser!, this.logonService.authenticatedPassword!, adres.id, this.logonService.xApiKey)
      .subscribe({
        next:
        
          response => {
           // if (response != undefined) {
           //   console.log('AdresesComponent ' + JSON.stringify(response));
           // }
           console.log('AdresesComponent deleted', response.status);
          },
        
        error: error => {
          this.errormessage = 'AdresesComponent Status: ' + error.status + ' details: ' + error.error.detail;
        }
      }
      )

    this.router.navigate(['/adresses']);
  }
}
