import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdresesComponent } from './adreses/adreses.component';
import { AppComponent } from './app.component';
import { AdresComponent } from './adres/adres.component';
import { AdresdetailComponent } from './adresdetail/adresdetail.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';


const routes: Routes = [  
  { path: 'adresses', component: AdresesComponent },
  { path: 'adresdetail/:id', component: AdresdetailComponent },
  { path: 'login', component: LoginComponent }, 
  { path: 'logout', component: LogoutComponent }, 
  { path: 'addadres' , component: AdresComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
