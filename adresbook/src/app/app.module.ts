import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ApiModule } from './core/modules/openapi';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';

import { CachingInterceptor } from './interceptors/caching.interceptor';
import { BASE_PATH } from './core/modules/openapi';
import { AdresesComponent } from './adreses/adreses.component';
import { AdresComponent } from './adres/adres.component';
import { AdresdetailComponent } from './adresdetail/adresdetail.component';

@NgModule({
  declarations: [
    AppComponent,
    AdresesComponent,
    AdresComponent,
    AdresdetailComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    ApiModule,
    HttpClientModule
  ],
  providers: [
//    { provide: BASE_PATH, useValue: "http:/localhost:8080/adres/api/v1" } 
    {provide: HTTP_INTERCEPTORS, useClass: CachingInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }