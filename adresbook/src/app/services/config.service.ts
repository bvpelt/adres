// src/app/services/config.service.ts
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  getApiUrl(): string {
    if (environment.production) {
      return window.env && window.env.API_BASE_URL || environment.apiUrl;
    } else {
      return environment.apiUrl;
    }
  }
}
