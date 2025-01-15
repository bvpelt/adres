import { Component } from '@angular/core';
import { Privilege, PrivilegeBody, Role, RoleBody } from '../core/modules/openapi';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { LogonService } from '../services/logon.service';
import { DbgmessageService } from '../services/dbgmessage.service';
import { HttpErrorResponse } from '@angular/common/http';
import { PrivilegeService } from '../services/privilege.service';
import { PrivilegechangedService } from '../services/privilegechanged.service';

@Component({
  selector: 'app-privilege',
  templateUrl: './privilege.component.html',
  styleUrl: './privilege.component.css'
})
export class PrivilegeComponent {
  privilege: Privilege = {} as Privilege;
  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(private router: Router,
    private privilegeService: PrivilegeService,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private privilegechangedService: PrivilegechangedService) {

    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }

  onSave(privilege: Privilege) {
    console.log('Add privilege: ', privilege);
    if (privilege != {} as Privilege) {
      this.dbgmessageService.add('PrivilegeComponent - Adding not empty privilege');
      const privilegebody: PrivilegeBody = { name: privilege.name };
      this.privilegeService.postPrivilege(this.logonService.xApiKey, privilegebody)
        .subscribe({
          next:
            response => {
              this.privilege = response.body as Privilege;
              this.privilegechangedService.emitNewPrivilege(privilege);
              this.dbgmessageService.debug('PrivilegeComponent - Emitted new privilege');
              this.router.navigate(['/privileges']);
              this.dbgmessageService.debug('PrivilegeComponent - Router navigate toe /privileges');
            },
          error: (error: HttpErrorResponse) => {
            this.errormessage = 'PrivilegeComponent - Status: ' + error.status + ' details: ' + error.error.error + ' url: ' + error.url;
          }
        });
    } else {
      this.dbgmessageService.debug('PrivilegeComponent - Skip empty user');
    }
  }

  cancel() {
    this.dbgmessageService.debug("PrivilegeComponent - Cancel user");
    this.router.navigate(['/privileges']);
  }
}
