import { Component } from '@angular/core';
import { LogonService } from '../services/logon.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username: string = "";
  password: string = "";

  constructor(private logonService: LogonService) {
    
  }

  onLogon() {
      this.logonService.doLogin(this.username, this.password);
  }
}
