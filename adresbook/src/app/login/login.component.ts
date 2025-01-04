import { Component, OnInit } from '@angular/core';
import { LogonService } from '../services/logon.service';
import { Router } from '@angular/router';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import { LoginResponse } from '../core/modules/openapi';
import { DbgmessageService } from '../services/dbgmessage.service';
import { DynamicconfigService } from '../services/dynamicconfig.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  showPassword = false;
  username: string = "";
  password: string = "";
  faEyeIcon = faEye;
  faEyeSlashIcon = faEyeSlash;

  loginResponse?: LoginResponse = undefined;
  errormessage?: string = undefined;

  constructor(private logonService: LogonService,
    private router: Router,
    private dynamicconfigService: DynamicconfigService,
    private dbgmessageService: DbgmessageService) {

  }

  onLogon() {
    this.logonService.doTestLogin(this.username, this.password)
      .subscribe({
        next:
          response => {
            if (response.body) {
              this.loginResponse = response.body;
              if (this.loginResponse!.authenticated) {
                this.logonService.isLoggedIn.next(true);
                this.logonService.authenticatedUser = this.username;
                this.logonService.authenticatedPassword = this.password;
                if ((response.body.token != undefined) && (response.body.token.length > 0)) {
                  this.dynamicconfigService.updateConfiguration(this.username, this.password, response.body.token);
                } else {
                  this.dynamicconfigService.updateConfiguration(this.username, this.password, undefined);
                }
                this.dbgmessageService.add('LogonService: authenticated');
              } else {
                this.logonService.isLoggedIn.next(false);
                this.logonService.authenticatedUser = undefined;
                this.logonService.authenticatedPassword = undefined;
                this.dbgmessageService.add('LogonService: not authenticated');
              }
            }
            this.router.navigate(['/adresses']);
          },
        error: error => {
          this.errormessage = 'Status: ' + error.status + ' details: ' + error.error.detail;
          this.logonService.authenticatedUser = undefined;
          this.logonService.authenticatedPassword = undefined;
          this.dbgmessageService.add('LogonService: ' + this.errormessage);
        }
      });
   
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }
}
