import { Component, OnInit } from '@angular/core';
import { LogonService } from '../services/logon.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent {


  constructor(private logonService: LogonService, private router: Router) {

  }

  ngOnInit(): void {
    this.logonService.doLogOut();
    this.router.navigate(['/adresses']);
  }

}
