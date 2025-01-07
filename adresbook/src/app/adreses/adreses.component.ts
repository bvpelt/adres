import { Component, Input, /* OnChanges,*/ OnInit, SimpleChanges } from '@angular/core';
import { Adres } from '../core/modules/openapi/model/adres';
import { Adresses } from '../core/modules/openapi/model/adresses';
import { AdresService } from '../services/adres.service';
import { LogonService } from '../services/logon.service';
import { Observable, Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { faPencil, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { DbgmessageService } from '../services/dbgmessage.service';
import { AdresseschangedService } from '../services/adresseschanged.service';
import { OpenadresService } from '../services/openadres.service';
import { PagedAdresses } from '../core/modules/openapi';


@Component({
  selector: 'app-adreses',
  templateUrl: './adreses.component.html',
  styleUrl: './adreses.component.css'
})
export class AdresesComponent implements OnInit /*, OnChanges */ {
  @Input() isFirstActivation: boolean = true;

  page: number = 1;
  size: number = 4;
  faPencilIcon = faPencil;
  faTrashCanIcon = faTrashCan;

  adreses: Adres[] = [];
  nextpage: number = 0;
  prevpage: number = 0;
  selectedAdres?: Adres = undefined;
  adreChangesubscription: Subscription | undefined;

  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(
    private openAdresService: OpenadresService,
    private router: Router,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private adresseschangedService: AdresseschangedService) {
    this.dbgmessageService.add('AdresesComponent - constructed subscription defined');
    this.isLoggedIn$ = this.logonService.isLoggedIn$;
    this.adreChangesubscription = this.adresseschangedService.newAdres$
      .subscribe(adres => {
        this.dbgmessageService.add('AdresesComponent - retrieve adresses go add: ' + JSON.stringify(adres));
        this.getAdresses(this.logonService.xApiKey, this.page, this.size);
      });
  }

  ngOnInit(): void {
    this.dbgmessageService.add('AdresesComponent - activated initial');
    this.errormessage = "";
    this.getAdresses(this.logonService.xApiKey, this.page, this.size);
    this.isFirstActivation = false;
  }
  
  getAdresses(xApiKey: string, page: number, size: number): void {
    this.openAdresService.getAdresses(xApiKey, page, size)
      .subscribe({
        next:
          response => {
            if (response.body) {
              const adresPage: PagedAdresses = response.body;
              const adresses: Adres[] = response.body as Adres[];
              this.adreses = adresPage.content!;
              this.dbgmessageService.add('AdresesComponent - before prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + adresPage.totalPages);
              if (adresPage.totalElements != undefined) {
                if (this.page + 1 <= adresPage.totalPages!) {
                  this.nextpage = this.page + 1;
                } else {
                  this.nextpage = 0;
                }
                if (this.page - 1 > 0) {
                  this.prevpage = this.page - 1;
                } else {
                  this.prevpage = 0;
                }
              }             
              this.dbgmessageService.add('AdresesComponent - after prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + adresPage.totalPages);
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

  onNextPage(): void {
    this.page = this.nextpage;
    this.getAdresses(this.logonService.xApiKey, this.page, this.size);
  }

  onPrevPage(): void {
    this.page = this.prevpage;
      this.getAdresses(this.logonService.xApiKey, this.page, this.size);
  }

  onDelete(adres: Adres): void {
    console.log("Delete adres")
    this.selectedAdres = adres;

    this.openAdresService.deleteAdres(adres.id, this.logonService.xApiKey)
      .subscribe({
        next:
          response => {
            // if (response != undefined) {
            //   console.log('AdresesComponent ' + JSON.stringify(response));
            // }
            this.dbgmessageService.add('AdresesComponent - deleted status: ' + response.status);
            this.adresseschangedService.emitNewAdres(adres);
          },

        error: error => {
          this.errormessage = 'AdresesComponent - Status: ' + error.status + ' details: ' + error.error.detail;
        }
      }
      )

    this.router.navigate(['/adresses']);
  }


  ngOnDestroy() {

    if (this.adreChangesubscription) {
      this.adreChangesubscription.unsubscribe();
      this.dbgmessageService.add('AdresesComponent - Subscription destroyed');
    }

    this.dbgmessageService.add('AdresesComponent - Destroyed');
  }

}
