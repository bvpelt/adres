import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { LogonService } from '../services/logon.service';
import { DbgmessageService } from '../services/dbgmessage.service';
import { Observable } from 'rxjs';
import { Role, RoleBody } from '../core/modules/openapi';
import { RoleService } from '../services/role.service';
import { RoleschangedService } from '../services/roleschanged.service';

@Component({
  selector: 'app-roledetail',
  templateUrl: './roledetail.component.html',
  styleUrl: './roledetail.component.css'
})
export class RoledetailComponent {
  role?: Role = undefined;
  password: string = "";
  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(private route: ActivatedRoute,
    private roleService: RoleService,
    private router: Router,
    private location: Location,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private roleschangedService: RoleschangedService) {
    this.getRole(this.logonService.xApiKey);
    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }


  getRole(xApiKey: string): void {
    const id: number = Number(this.route.snapshot.paramMap.get('id'));

    this.roleService.getRole(id, xApiKey)
      .subscribe({
        next:
          response => {
            if (response.body) {
              this.role = response.body;
            }
          },
        error: error => {
          this.errormessage = 'RoledetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });
  }

  onUpdate(role: Role) {
    var rolebody: RoleBody;
    
    rolebody = { rolename: role.rolename, description: role.description};
    
    this.roleService.patchRole(role.id, this.logonService.xApiKey, rolebody)
      .subscribe({
        next:
          response => {
            this.role = response.body as Role;
            this.roleschangedService.emitNewRole(role);
            this.dbgmessageService.debug('RoledetailComponent - Emitted new user');
            this.router.navigate(['/users']);
            this.dbgmessageService.debug('RoledetailComponent - Router navigate toe /users');
          },
        error: error => {
          this.errormessage = 'RoledetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });

    this.router.navigate(['/roles']);
  }

  cancel() {
    this.location.back();
  }

}
