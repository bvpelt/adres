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


const routes: Routes = [
  { path: 'adresses', component: AdresesComponent },
  { path: 'addadres', component: AdresComponent },
  { path: 'adresdetail/:id', component: AdresdetailComponent },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'persons', component: PersonsComponent },
  { path: 'addperson', component: PersonComponent },
  { path: 'persondetail/:id', component: PersondetailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
