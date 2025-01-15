import { Component, OnInit } from '@angular/core';
import { LogonService } from '../services/logon.service';
import { Observable, Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { faPencil, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { DbgmessageService } from '../services/dbgmessage.service';
import { PagedRoles, Role } from '../core/modules/openapi';

import { RoleschangedService } from '../services/roleschanged.service';
import { RoleService } from '../services/role.service';

@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrl: './roles.component.css'
})
export class RolesComponent implements OnInit {
  page: number = 1;
  size: number = 4;
  faPencilIcon = faPencil;
  faTrashCanIcon = faTrashCan;

  roles: Role[] = [];
  nextpage: number = 0;
  prevpage: number = 0;
  totalpages: number = 0;
  selectedRole?: Role = undefined;
  rolesChangesubscription: Subscription | undefined;

  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(
    private roleService: RoleService,
    private router: Router,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private RoleschangedService: RoleschangedService) {
    this.dbgmessageService.debug('RolesComponent - constructed subscription defined');
    this.isLoggedIn$ = this.logonService.isLoggedIn$;
    this.rolesChangesubscription = this.RoleschangedService.newRole$
      .subscribe(role => {
        this.dbgmessageService.debug('RolesComponent - retrieve role go add: ' + JSON.stringify(role));
        this.getRoles(this.logonService.xApiKey, this.page, this.size);
      });
  }

  ngOnInit(): void {
    this.dbgmessageService.debug('RolesComponent - activated initial');
    this.errormessage = "";
    this.getRoles(this.logonService.xApiKey, this.page, this.size);
  }

  getRoles(xApiKey: string, page: number, size: number): void {
    this.roleService.getRoles(xApiKey, page, size)
      .subscribe({
        next:
          response => {
            if (response.body) {
              const rolePage: PagedRoles = response.body;
              //const roles: Role[] = response.body as Role[];
              this.roles = rolePage.content!;
              this.dbgmessageService.trace('RolesComponent - before prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + rolePage.totalPages);
              if (rolePage.totalElements != undefined) {
                this.totalpages = rolePage.totalPages!;
                if (this.page + 1 <= rolePage.totalPages!) {
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
              this.dbgmessageService.trace('RolesComponent - after prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + rolePage.totalPages);
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

  onSelect(role: Role): void {
    this.selectedRole = role;
    this.router.navigate(['/roledetail', role.id]);
  }

  onNextPage(): void {
    this.page = this.nextpage;
    this.getRoles(this.logonService.xApiKey, this.page, this.size);
  }

  onPrevPage(): void {
    this.page = this.prevpage;
    this.getRoles(this.logonService.xApiKey, this.page, this.size);
  }

  onDelete(role: Role): void {
    console.log("Delete role")
    this.selectedRole = role;

    this.roleService.deleteRole(role.id, this.logonService.xApiKey)
      .subscribe({
        next:
          response => {
            this.dbgmessageService.debug('RolesComponent - deleted status: ' + response.status);
            this.RoleschangedService.emitNewRole(role);
          },
        error: error => {
          this.errormessage = 'RolesComponent - Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });

    this.router.navigate(['/roles']);
  }

  ngOnDestroy() {

    if (this.rolesChangesubscription) {
      this.rolesChangesubscription.unsubscribe();
      this.dbgmessageService.trace('RolesComponent - Subscription destroyed');
    }

    this.dbgmessageService.trace('RolesComponent - Destroyed');
  }
}
