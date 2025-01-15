import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LogonService } from '../services/logon.service';
import { DbgmessageService } from '../services/dbgmessage.service';
import { Observable } from 'rxjs';
import { Privilege, PrivilegeBody } from '../core/modules/openapi';
import { PrivilegeService } from '../services/privilege.service';
import { PrivilegechangedService } from '../services/privilegechanged.service';
@Component({
  selector: 'app-privilegedetail',
  templateUrl: './privilegedetail.component.html',
  styleUrl: './privilegedetail.component.css'
})
export class PrivilegedetailComponent {
  privilege?: Privilege = undefined;
  password: string = "";
  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(private route: ActivatedRoute,
    private privilegeService: PrivilegeService,
    private router: Router,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private privilegechangedService: PrivilegechangedService) {
    this.getUser(this.logonService.xApiKey);
    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }


  getUser(xApiKey: string): void {
    const id: number = Number(this.route.snapshot.paramMap.get('id'));

    this.privilegeService.getPrivilege(id, xApiKey)
      .subscribe({
        next:
          response => {
            if (response.body) {
              this.privilege = response.body;
            }
          },
        error: error => {
          this.errormessage = 'RoledetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });
  }

  onUpdate(privilege: Privilege) {
    var privilegebody: PrivilegeBody;
    
    privilegebody = { name: privilege.name};
    
    this.privilegeService.patchPrivilege(privilege.id, this.logonService.xApiKey, privilegebody)
      .subscribe({
        next:
          response => {
            this.privilege = response.body as Privilege;
            this.privilegechangedService.emitNewPrivilege(privilege);
            this.dbgmessageService.debug('RoledetailComponent - Emitted new user');
            this.router.navigate(['/privileges']);
            this.dbgmessageService.debug('RoledetailComponent - Router navigate toe /privileges');
          },
        error: error => {
          this.errormessage = 'RoledetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });

    this.router.navigate(['/privileges']);
  }

  cancel() {
    this.router.navigate(['/privileges']);
  }
}
