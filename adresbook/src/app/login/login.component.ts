import { AfterViewInit, Component, ElementRef, ViewChild  } from '@angular/core';
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
export class LoginComponent implements AfterViewInit {
  @ViewChild('usernameInput') usernameInput!: ElementRef; 

  showPassword = false;
  username: string = "";
  password: string = "";
  email: string = "";
  faEyeIcon = faEye;
  faEyeSlashIcon = faEyeSlash;

  loginResponse?: LoginResponse = undefined;
  errormessage?: string = undefined;

  constructor(private logonService: LogonService,
    private router: Router,
    private dynamicconfigService: DynamicconfigService,
    private dbgmessageService: DbgmessageService) {
  }

  ngAfterViewInit() {
    this.usernameInput.nativeElement.focus(); 
  }

  onLogon() {
    this.logonService.doLogin(this.username, this.password, this.email)
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
                this.dbgmessageService.debug('LogonService: authenticated');
                this.router.navigate(['/adresses']);
              } else {
                this.logonService.isLoggedIn.next(false);
                this.logonService.authenticatedUser = undefined;
                this.logonService.authenticatedPassword = undefined;
                this.dbgmessageService.info('LogonService: not authenticated');
                this.errormessage = 'Invalid login attempt';
              }
            }
          },
        error: error => {
          //this.errormessage = 'Status: ' + error.status + ' details: ' + error.error.message;
          this.errormessage = 'Invalid login attempt';
          this.logonService.authenticatedUser = undefined;
          this.logonService.authenticatedPassword = undefined;
          //this.dbgmessageService.info('LogonService: ' + JSON.stringify(error));
          this.dbgmessageService.error('LogonService: status: ' + error.status + ' details: ' + error.error.message);
          this.router.navigate(['/login']);
        }
      });

  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }
}
