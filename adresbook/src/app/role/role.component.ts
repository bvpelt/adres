import { Component } from '@angular/core';
import { Role, RoleBody } from '../core/modules/openapi';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { LogonService } from '../services/logon.service';
import { DbgmessageService } from '../services/dbgmessage.service';
import { HttpErrorResponse } from '@angular/common/http';
import { RoleService } from '../services/role.service';
import { RoleschangedService } from '../services/roleschanged.service';


@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrl: './role.component.css'
})
export class RoleComponent {
  role: Role = {} as Role;
  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(private router: Router,
    private roleService: RoleService,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private roleschangedService: RoleschangedService) {

    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }

  onSave(role: Role) {
    console.log('Add role: ', role);
    if (role != {} as Role) {
      this.dbgmessageService.add('RoleComponent - Adding not empty user');
      const rolebody: RoleBody = { rolename: role.rolename, description: role.description };
      this.roleService.postRole(this.logonService.xApiKey, rolebody)
        .subscribe({
          next:
            response => {
              this.role = response.body as Role;
              this.roleschangedService.emitNewRole(role);
              this.dbgmessageService.debug('RoleComponent - Emitted new user');
              this.router.navigate(['/roles']);
              this.dbgmessageService.debug('RoleComponent - Router navigate toe /roles');
            },
          error: (error: HttpErrorResponse) => {
            this.errormessage = 'RoleComponent - Status: ' + error.status + ' details: ' + error.error.error + ' url: ' + error.url;
          }
        });
    } else {
      this.dbgmessageService.debug('RoleComponent - Skip empty user');
    }
  }

  cancel() {
    this.dbgmessageService.debug("RoleComponent - Cancel user");
    this.router.navigate(['/roles']);
  }
}
