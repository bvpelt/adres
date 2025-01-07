import { Component } from '@angular/core';
import { DbgmessageService } from '../services/dbgmessage.service';

@Component({
  selector: 'app-dbgmessages',
  templateUrl: './dbgmessages.component.html',
  styleUrl: './dbgmessages.component.css'
})
export class DbgmessagesComponent {

  constructor(public dbgMessageService: DbgmessageService) {

  }

}