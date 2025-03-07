import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here
import { HttpClientModule } from '@angular/common/http';
import { ServiceWorkerModule } from '@angular/service-worker';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { environment } from '../environments/environment';
import { ConfigService } from './services/config.service';

import { ApiModule, Configuration, ConfigurationParameters } from './core/modules/openapi';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AdresesComponent } from './adreses/adreses.component';
import { AdresComponent } from './adres/adres.component';
import { AdresdetailComponent } from './adresdetail/adresdetail.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { DbgmessagesComponent } from './dbgmessages/dbgmessages.component';
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
import { PersonselectComponent } from './personselect/personselect.component';


export function apiConfigFactory(configService: ConfigService): Configuration { // Inject ConfigService
  const params: ConfigurationParameters = {
    // set configuration parameters here.
    basePath: configService.getApiUrl(),
  }
  return new Configuration(params);
}

@NgModule({
  declarations: [
    AppComponent,
    AdresesComponent,
    AdresComponent,
    AdresdetailComponent,
    LoginComponent,
    LogoutComponent,
    DbgmessagesComponent,
    PersonsComponent,
    PersonComponent,
    PersondetailComponent,
    UsersComponent,
    UserComponent,
    UserdetailComponent,
    RolesComponent,
    RoleComponent,
    RoledetailComponent,
    PrivilegesComponent,
    PrivilegeComponent,
    PrivilegedetailComponent,
    PersonselectComponent,
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    FormsModule,
    AppRoutingModule,
    ApiModule.forRoot(() => new Configuration()), // Pass a dummy factory function

    HttpClientModule,
    environment.enableServiceWorker ? ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000',
      // Add a unique identifier to the service worker configuration
    }) : []
  ],
  providers: [
    ConfigService,
    {
      provide: Configuration,
      useFactory: apiConfigFactory,
      deps: [ConfigService],
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
