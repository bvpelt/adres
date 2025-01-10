import { Component, OnInit } from '@angular/core';
import { LogonService } from '../services/logon.service';
import { Observable, Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { faPencil, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { DbgmessageService } from '../services/dbgmessage.service';
import { PagedUsers, User } from '../core/modules/openapi';
import { UserService } from '../services/user.service';
import { UserschangedService } from '../services/userschanged.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit {
  page: number = 1;
  size: number = 4;
  faPencilIcon = faPencil;
  faTrashCanIcon = faTrashCan;

  users: User[] = [];
  nextpage: number = 0;
  prevpage: number = 0;
  totalpages: number = 0;
  selectedUser?: User = undefined;
  adresChangesubscription: Subscription | undefined;

  errormessage?: string = undefined;

  isLoggedIn$: Observable<boolean>;

  constructor(
    private userService: UserService,
    private router: Router,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService,
    private userschangedService: UserschangedService) {
    this.dbgmessageService.debug('UsersComponent - constructed subscription defined');
    this.isLoggedIn$ = this.logonService.isLoggedIn$;
    this.adresChangesubscription = this.userschangedService.newUser$
      .subscribe(user => {
        this.dbgmessageService.debug('UsersComponent - retrieve adresses go add: ' + JSON.stringify(user));
        this.getUsers(this.logonService.xApiKey, this.page, this.size);
      });
  }

  ngOnInit(): void {
    this.dbgmessageService.debug('UsersComponent - activated initial');
    this.errormessage = "";
    this.getUsers(this.logonService.xApiKey, this.page, this.size);
  }

  getUsers(xApiKey: string, page: number, size: number): void {
    this.userService.getUsers(xApiKey, page, size)
      .subscribe({
        next:
          response => {
            if (response.body) {
              const userPage: PagedUsers = response.body;
              const users: User[] = response.body as User[];
              this.users = userPage.content!;
              this.dbgmessageService.trace('UsersComponent - before prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + userPage.totalPages);
              if (userPage.totalElements != undefined) {
                this.totalpages = userPage.totalPages!;
                if (this.page + 1 <= userPage.totalPages!) {
                  this.nextpage = this.page + 1;
                } else {
                  this.nextpage = 0;
                }
                if (this.page - 1 > 0) {
                  this.prevpage = this.page - 1;
                } else {
                  this.prevpage = 0;
                }
              }
              this.dbgmessageService.trace('UsersComponent - after prevpage: ' + this.prevpage + ' page: ' + this.page + ' nextpage: ' + this.nextpage + ' total: ' + userPage.totalPages);
            }
          },

        error: error => {
          /*
          console.log('error: ' + JSON.stringify(error));
          console.log('error message: ' + error.message);
          console.log('error status: ' + error.status);
          console.log('error error: ' + JSON.stringify(error.error));
          console.log('error detail: ' + error.error.detail);
          */
          this.errormessage = 'Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });
  }

  onSelect(user: User): void {
    this.selectedUser = user;
    this.router.navigate(['/userdetail', user.id]);
  }

  onNextPage(): void {
    this.page = this.nextpage;
    this.getUsers(this.logonService.xApiKey, this.page, this.size);
  }

  onPrevPage(): void {
    this.page = this.prevpage;
    this.getUsers(this.logonService.xApiKey, this.page, this.size);
  }

  onDelete(user: User): void {
    console.log("Delete user")
    this.selectedUser = user;

    this.userService.deleteUser(user.id, this.logonService.xApiKey)
      .subscribe({
        next:
          response => {
            this.dbgmessageService.debug('UsersComponent - deleted status: ' + response.status);
            this.userschangedService.emitNewUser(user);
          },
        error: error => {
          this.errormessage = 'UsersComponent - Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });

    this.router.navigate(['/users']);
  }

  ngOnDestroy() {

    if (this.adresChangesubscription) {
      this.adresChangesubscription.unsubscribe();
      this.dbgmessageService.trace('UsersComponent - Subscription destroyed');
    }

    this.dbgmessageService.trace('UsersComponent - Destroyed');
  }

}
