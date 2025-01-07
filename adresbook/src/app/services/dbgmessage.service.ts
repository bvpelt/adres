import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DbgmessageService {
  private static readonly LEVELS: string[] = ["FAT ", "ERR ", "WARN", "INFO", "DBG ", "TRC "]; // levels 0 FAT, 1 ERR, 2 WARN, 3 INFO, 4 DBG, 5 TRC

  messages: string[] = [];
  maxlevel: number = this.getLevelIndex("INFO");

  constructor() { }

  addStatus(status: string, message: string) {

    if (this.getLevelIndex(status) <= this.maxlevel) {
      var mymessage: string;

      const now = new Date();
      const isoTimestamp = now.toISOString(); // Get ISO 8601 timestamp

      mymessage = isoTimestamp + " " + status + " - " + message;
      this.messages.push(mymessage);
    }
  }

  add(message: string) {
    this.addStatus("INFO", message);
  }

  trace(message: string) {
    this.addStatus("TRC ", message);
  }

  debug(message: string) {
    this.addStatus("DBG ", message);
  }

  info(message: string) {
    this.addStatus("INFO", message);
  }

  warn(message: string) {
    this.addStatus("WARN", message);
  }

  error(message: string) {
    this.addStatus("ERR ", message);
  }

  fatal(message: string) {
    this.addStatus("FAT ", message);
  }

  clear() {
    this.messages = [];
  }

  private getLevelIndex(level: string): number {
    return DbgmessageService.LEVELS.indexOf(level);
  }

  public setLevel(level: string): void {
    var newLevel = this.getLevelIndex(level);
    if (newLevel >= 0) {
      this.maxlevel = newLevel;
    }
  }
}