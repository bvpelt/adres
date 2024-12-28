import { Component, OnInit } from '@angular/core';
import { LogonService } from '../services/logon.service';
import { Router } from '@angular/router';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';

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
  errormessage: string = "";

  constructor(private logonService: LogonService, private router: Router) {
    
  }

  onLogon() {
    /*
      this.logonService.doLogin(this.username, this.password);
      console.log('LoginComponent after doLogin - authenticated user: ' + this.logonService.authenticatedUser + ' authenticated password: ' + this.logonService.authenticatedPassword + ' isLoggedIn: ' + this.logonService.isLoggedIn$)
      this.router.navigate(['/adresses']);
      */

      this.logonService.postLogin(this.logonService.xApiKey, this.username, this.password)
      .subscribe({
        next:
          response => {
            if (response.body) {
              //this.loginResponse = response.body;
              if (response.body.authenticated) {
                this.logonService.isLoggedIn = true;
                this.logonService.authenticatedUser = this.username;
                this.logonService.authenticatedPassword = this.password;
                console.log('LoginComponent: authenticated');
                this.router.navigate(['/adresses']);
              } else {
                this.logonService.isLoggedIn = false;
                this.logonService.authenticatedUser = undefined
                this.logonService.authenticatedPassword = undefined;      
                console.log('LogonService: not authenticated');
              }
            }
          },
        error: error => {
          this.errormessage = 'Status: ' + error.status + ' details: ' + error.error.detail;
          this.logonService.authenticatedUser = undefined;
          this.logonService.authenticatedPassword = undefined;
          console.log('LogonService: ' + this.errormessage);
        }
  });
}

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }
}
