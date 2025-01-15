import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdresesComponent } from './adreses/adreses.component';
import { AppComponent } from './app.component';
import { AdresComponent } from './adres/adres.component';
import { AdresdetailComponent } from './adresdetail/adresdetail.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { PersonsComponent } from './persons/persons.component';
import { PersonComponent } from './person/person.component';
import { PersondetailComponent } from './persondetail/persondetail.component';
import { UsersComponent } from './users/users.component';
import { UserComponent } from './user/user.component';
import { UserdetailComponent } from './userdetail/userdetail.component';
import { RolesComponent } from './roles/roles.component';
import { RoleComponent } from './role/role.component';
import { RoledetailComponent } from './roledetail/roledetail.component';
import { PrivilegesComponent } from './privileges/privileges.component';
import { PrivilegeComponent } from './privilege/privilege.component';
import { PrivilegedetailComponent } from './privilegedetail/privilegedetail.component';


const routes: Routes = [
  { path: 'adresses', component: AdresesComponent },
  { path: 'addadres', component: AdresComponent },
  { path: 'adresdetail/:id', component: AdresdetailComponent },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'persons', component: PersonsComponent },
  { path: 'addperson', component: PersonComponent },
  { path: 'persondetail/:id', component: PersondetailComponent },
  { path: 'users', component: UsersComponent },
  { path: 'adduser', component: UserComponent },
  { path: 'userdetail/:id', component: UserdetailComponent },
  { path: 'roles', component: RolesComponent },
  { path: 'addrole', component: RoleComponent },
  { path: 'roledetail/:id', component: RoledetailComponent },
  { path: 'privileges', component: PrivilegesComponent },
  { path: 'addprivilege', component: PrivilegeComponent },
  { path: 'privilegedetail/:id', component: PrivilegedetailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
