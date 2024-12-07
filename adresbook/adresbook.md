# Adresbook

## Generated

To get a module based angular app use:
```bash
ng new -g --no-standalone adresbook
```

## Generating code from openapi spec


- install openapi (see https://pguso.medium.com/using-openapi-generator-in-angular-projects-4c2813f55a91)
- generate code from openapi specification from directory $HOME/adres/adresbook execute the following command

Make sure the openapi-generator is installed

```bash
npm install @openapitools/openapi-generator-cli -g
```

```bash
npx openapi-generator-cli generate -i ../src/main/resources/adres.0.0.4.yaml -g typescript-angular -o src/app/core/modules/openapi --additional-properties fileNaming=kebab-case,withInterfaces=true,ngVersion=17.3.12 --generate-alias-as-model
```

- import the generated module in our app.module.ts:

Old app.module.ts not available
```typescript
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
```


Updated app.module.ts
```typescript
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ApiModule } from './core/modules/openapi';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ApiModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

## Font Awesom
See https://www.npmjs.com/package/@fortawesome/angular-fontawesome

