import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LogonService } from '../services/logon.service';
import { DbgmessageService } from '../services/dbgmessage.service';
import { Observable } from 'rxjs';
import { User, UserBody } from '../core/modules/openapi';
import { UserService } from '../services/user.service';
import { UserschangedService } from '../services/userschanged.service';

@Component({
  selector: 'app-userdetail',
  templateUrl: './userdetail.component.html',
  styleUrl: './userdetail.component.css'
})
export class UserdetailComponent {
  user?: User = undefined;
  password: string = "";
  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(private route: ActivatedRoute,
    private userService: UserService,
    private router: Router,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private userschangedService: UserschangedService) {
    this.getUser(this.logonService.xApiKey);
    this.isLoggedIn$ = this.logonService.isLoggedIn$;
  }


  getUser(xApiKey: string): void {
    const id: number = Number(this.route.snapshot.paramMap.get('id'));

    this.userService.getUser(id, xApiKey)
      .subscribe({
        next:
          response => {
            if (response.body) {
              this.user = response.body;
            }
          },
        error: error => {
          this.errormessage = 'UserdetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });
  }

  onUpdate(user: User) {
    var userbody: UserBody;
    if (this.password.length > 0) {
      userbody = { username: user.username, password: this.password, email: user.email, phone: user.phone };
    } else {
      userbody = { username: user.username, email: user.email, phone: user.phone };
    }
    this.userService.patchUser(user.id, this.logonService.xApiKey, userbody)
      .subscribe({
        next:
          response => {
            this.user = response.body as User;
            this.userschangedService.emitNewUser(user);
            this.dbgmessageService.debug('UserdetailComponent - Emitted new user');
            this.router.navigate(['/users']);
            this.dbgmessageService.debug('UserdetailComponent - Router navigate toe /users');
          },
        error: error => {
          this.errormessage = 'UserdetailComponent Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });

    this.router.navigate(['/users']);
  }

  cancel() {
    this.router.navigate(['/users']);
  }
}
