import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DbgmessageService {
  messages: string[] = [];

  constructor() { }

  add(message: string) {
    const now = new Date();
    const isoTimestamp = now.toISOString(); // Get ISO 8601 timestamp
    this.messages.push(isoTimestamp + ': ' + message);
  }

  clear() {
    this.messages = [];
  }
}