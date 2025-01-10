import { Component } from '@angular/core';
import { User, UserBody } from '../core/modules/openapi';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { LogonService } from '../services/logon.service';
import { DbgmessageService } from '../services/dbgmessage.service';
import { HttpErrorResponse } from '@angular/common/http';
import { UserService } from '../services/user.service';
import { UserschangedService } from '../services/userschanged.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent {
  user: User = {} as User;
  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(private router: Router,
    private userService: UserService,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private userschangedService: UserschangedService) {

    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }

  onSave(user: User) {
    if (user != {} as User) {
      this.dbgmessageService.add('UserComponent - Adding not empty user');
      const userbody: UserBody = { username: user.username, password: user.password, email: user.email, phone: user.phone} ;
      this.userService.postUser(this.logonService.xApiKey, userbody)
        .subscribe({
          next:
            response => {
              this.user = response.body as User;
              this.userschangedService.emitNewUser(user);
              this.dbgmessageService.debug('UserComponent - Emitted new user');
              this.router.navigate(['/users']);
              this.dbgmessageService.debug('UserComponent - Router navigate toe /users');
            },
          error: (error: HttpErrorResponse) => {
            this.errormessage = 'UserComponent - Status: ' + error.status + ' details: ' + error.error.error + ' url: ' + error.url;
          }
        });
    } else {
      this.dbgmessageService.debug('UserComponent - Skip empty user');
    }
  }

  cancel() {
    this.dbgmessageService.debug("UserComponent - Cancel user");
    this.router.navigate(['/users']);
  }
}
