import { Component, OnInit } from '@angular/core';
import { LogonService } from '../services/logon.service';
import { Observable, Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { faPencil, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { DbgmessageService } from '../services/dbgmessage.service';
import { PagedPrivileges, PagedRoles, Privilege } from '../core/modules/openapi';
import { PrivilegechangedService } from '../services/privilegechanged.service';
import { PrivilegeService } from '../services/privilege.service';

@Component({
  selector: 'app-privileges',
  templateUrl: './privileges.component.html',
  styleUrl: './privileges.component.css'
})
export class PrivilegesComponent implements OnInit {
  page: number = 1;
  size: number = 4;
  faPencilIcon = faPencil;
  faTrashCanIcon = faTrashCan;

  privileges: Privilege[] = [];
  nextpage: number = 0;
  prevpage: number = 0;
  totalpages: number = 0;
  selectedPrivilege?: Privilege = undefined;
  privilegeChangesubscription: Subscription | undefined;

  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(
    private privilegeService: PrivilegeService,
    private router: Router,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private privilegechangedService: PrivilegechangedService) {
    this.dbgmessageService.debug('PrivilegesComponent - constructed subscription defined');
    this.isLoggedIn$ = this.logonService.isLoggedIn$;
    this.privilegeChangesubscription = this.privilegechangedService.newPrivilege$
      .subscribe(privilege => {
        this.dbgmessageService.debug('PrivilegesComponent - retrieve privilege go add: ' + JSON.stringify(privilege));
        this.getPrivileges(this.logonService.xApiKey, this.page, this.size);
      });
  }

  ngOnInit(): void {
    this.dbgmessageService.debug('PrivilegesComponent - activated initial');
    this.errormessage = "";
    this.getPrivileges(this.logonService.xApiKey, this.page, this.size);
  }

  getPrivileges(xApiKey: string, page: number, size: number): void {
    this.privilegeService.getPrivileges(xApiKey, page, size)
      .subscribe({
        next:
          response => {
            if (response.body) {
              const privilegePage: PagedPrivileges = response.body;
              //const privileges: Privilege[] = response.body as RoPrivilegele[];
              this.privileges = privilegePage.content!;
              this.dbgmessageService.trace('PrivilegesComponent - before prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + privilegePage.totalPages);
              if (privilegePage.totalElements != undefined) {
                this.totalpages = privilegePage.totalPages!;
                if (this.page + 1 <= privilegePage.totalPages!) {
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
              this.dbgmessageService.trace('PrivilegesComponent - after prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + privilegePage.totalPages);
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

  onSelect(privilege: Privilege): void {
    this.selectedPrivilege = privilege;
    this.router.navigate(['/privilegedetail', privilege.id]);
  }

  onNextPage(): void {
    this.page = this.nextpage;
    this.getPrivileges(this.logonService.xApiKey, this.page, this.size);
  }

  onPrevPage(): void {
    this.page = this.prevpage;
    this.getPrivileges(this.logonService.xApiKey, this.page, this.size);
  }

  onDelete(privilege: Privilege): void {
    console.log("Delete privilege")
    this.selectedPrivilege = privilege;

    this.privilegeService.deletePrivilege(privilege.id, this.logonService.xApiKey)
      .subscribe({
        next:
          response => {
            this.dbgmessageService.debug('PrivilegesComponent - deleted status: ' + response.status);
            this.privilegechangedService.emitNewPrivilege(privilege);
          },
        error: error => {
          this.errormessage = 'PrivilegesComponent - Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });

    this.router.navigate(['/privileges']);
  }

  ngOnDestroy() {

    if (this.privilegeChangesubscription) {
      this.privilegeChangesubscription.unsubscribe();
      this.dbgmessageService.trace('PrivilegesComponent - Subscription destroyed');
    }

    this.dbgmessageService.trace('PrivilegesComponent - Destroyed');
  }
}
