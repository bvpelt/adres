import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ApiModule } from './core/modules/openapi';
import { HttpClientModule } from '@angular/common/http';
import { BASE_PATH } from './core/modules/openapi';
import { AdresesComponent } from './adreses/adreses.component';

@NgModule({
  declarations: [
    AppComponent,
    AdresesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ApiModule,
    HttpClientModule
  ],
  providers: [
//    { provide: BASE_PATH, useValue: "http:/localhost:8080/adres/api/v1" } 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }