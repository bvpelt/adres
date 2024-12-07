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

  constructor(private logonService: LogonService, private router: Router) {
    
  }

  onLogon() {
      this.logonService.doLogin(this.username, this.password);
      this.router.navigate(['/adresses']);
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }
}
