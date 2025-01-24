import { Component } from '@angular/core';
import { LogonService } from '../services/logon.service';
import { Router } from '@angular/router';
import { DynamicconfigService } from '../services/dynamicconfig.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent {


  constructor(private logonService: LogonService, private dynamicconfigService: DynamicconfigService, private router: Router) {

  }

  ngOnInit(): void {
    this.logonService.doLogOut();
    this.dynamicconfigService.updateConfiguration("", "", "");
    this.router.navigate(['/adresses']);
  }

}
