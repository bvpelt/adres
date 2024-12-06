import { Component } from '@angular/core';
import { LogonService } from '../services/logon.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username: string = "";
  password: string = "";

  constructor(private logonService: LogonService, private router: Router) {
    
  }

  onLogon() {
      this.logonService.doLogin(this.username, this.password);
      this.router.navigate(['/adresses']);
  }
}
