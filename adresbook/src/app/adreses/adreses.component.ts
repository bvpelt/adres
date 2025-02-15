import { Component, Input, OnInit } from '@angular/core';
import { Adres } from '../core/modules/openapi/model/adres';
import { LogonService } from '../services/logon.service';
import { Observable, Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { faPencil, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { DbgmessageService } from '../services/dbgmessage.service';
import { AdresseschangedService } from '../services/adresseschanged.service';
import { PagedAdresses } from '../core/modules/openapi';
import { AdresService } from '../services/adres.service';


@Component({
  selector: 'app-adreses',
  templateUrl: './adreses.component.html',
  styleUrl: './adreses.component.css'
})
export class AdresesComponent implements OnInit {
  page: number = 1;
  size: number = 4;
  faPencilIcon = faPencil;
  faTrashCanIcon = faTrashCan;

  adreses: Adres[] = [];
  nextpage: number = 0;
  prevpage: number = 0;
  totalpages: number = 0;
  selectedAdres?: Adres = undefined;
  adresChangesubscription: Subscription | undefined;

  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(
    private adresService: AdresService,
    private router: Router,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private adresseschangedService: AdresseschangedService) {
    this.dbgmessageService.debug('AdresesComponent - constructed subscription defined');
    this.isLoggedIn$ = this.logonService.isLoggedIn$;
    this.adresChangesubscription = this.adresseschangedService.newAdres$
      .subscribe(adres => {
        this.dbgmessageService.debug('AdresesComponent - retrieve adresses go add: ' + JSON.stringify(adres));
        this.getAdresses(this.logonService.xApiKey, this.page, this.size);
      });
  }

  ngOnInit(): void {
    this.dbgmessageService.debug('AdresesComponent - activated initial');
    this.errormessage = "";
    this.getAdresses(this.logonService.xApiKey, this.page, this.size);
  }

  getAdresses(xApiKey: string, page: number, size: number): void {
    this.adresService.getAdresses(xApiKey, page, size)
      .subscribe({
        next:
          response => {
            if (response.body) {
              const adresPage: PagedAdresses = response.body;
              //const adresses: Adres[] = response.body as Adres[];
              this.adreses = adresPage.content!;
              this.dbgmessageService.trace('AdresesComponent - before prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + adresPage.totalPages);
              if (adresPage.totalElements != undefined) {
                this.totalpages = adresPage.totalPages!;
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
              this.dbgmessageService.trace('AdresesComponent - after prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + adresPage.totalPages);
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

    this.adresService.deleteAdres(adres.id, this.logonService.xApiKey)
      .subscribe({
        next:
          response => {
            this.dbgmessageService.debug('AdresesComponent - deleted status: ' + response.status);
            this.adresseschangedService.emitNewAdres(adres);
          },
        error: error => {
          this.errormessage = 'AdresesComponent - Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });

    this.router.navigate(['/adresses']);
  }

  ngOnDestroy() {

    if (this.adresChangesubscription) {
      this.adresChangesubscription.unsubscribe();
      this.dbgmessageService.trace('AdresesComponent - Subscription destroyed');
    }

    this.dbgmessageService.trace('AdresesComponent - Destroyed');
  }

}
