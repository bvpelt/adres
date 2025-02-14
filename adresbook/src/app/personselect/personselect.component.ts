import { Component, input, Input } from '@angular/core';
import { Adres, Person } from '../core/modules/openapi';
import { ActivatedRoute, Router } from '@angular/router';
import { AdresService } from '../services/adres.service';
import { LogonService } from '../services/logon.service';
import { DbgmessageService } from '../services/dbgmessage.service';

@Component({
  selector: 'app-personselect',
  templateUrl: './personselect.component.html',
  styleUrl: './personselect.component.css'
})
export class PersonselectComponent {

  @Input() adres?: Adres;
  persons: Person[] = [];

  constructor(private route: ActivatedRoute,
    private adresService: AdresService,
    private router: Router,
    private location: Location,
    private logonService: LogonService,
    private dbgmessageService: DbgmessageService) {
      this.dbgmessageService.info("PersonselectComponent received adres: " + ((this.adres == undefined) ? "" : JSON.stringify(this.adres)));
    }

    getPersons() {
      
    }
}
