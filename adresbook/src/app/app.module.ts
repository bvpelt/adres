import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ApiModule, Configuration, ConfigurationParameters } from './core/modules/openapi';
import { environment } from '../environments/environment';

import { HttpClientModule } from '@angular/common/http';

import { BASE_PATH } from './core/modules/openapi';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';

import { AdresesComponent } from './adreses/adreses.component';
import { AdresComponent } from './adres/adres.component';
import { AdresdetailComponent } from './adresdetail/adresdetail.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { DbgmessagesComponent } from './dbgmessages/dbgmessages.component';
import { PersonsComponent } from './persons/persons.component';
import { PersonComponent } from './person/person.component';
import { PersondetailComponent } from './persondetail/persondetail.component';

export function apiConfigFactory(): Configuration {
  const params: ConfigurationParameters = {
    // set configuration parameters here.
    basePath: environment.apiUrl,
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
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    FormsModule,
    AppRoutingModule,
    ApiModule.forRoot(apiConfigFactory),
    HttpClientModule,
    environment.enableServiceWorker ? ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000',
      // Add a unique identifier to the service worker configuration   
    }) : []
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }