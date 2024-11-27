import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Adres } from '../core/modules/openapi/model/adres';
import { AdresService } from '../services/adres.service';

@Component({
  selector: 'app-adresdetail',
  templateUrl: './adresdetail.component.html',
  styleUrl: './adresdetail.component.css'
})
export class AdresdetailComponent {
  xApiKey: string = 'f0583805-03f6-4c7f-8e40-f83f55b7c077';
  adres?: Adres = undefined;
  errormessage?: string = undefined;

  constructor(private route: ActivatedRoute, private adresService: AdresService, private location: Location) {
    this.getAdres(this.xApiKey);
  }


  getAdres(xApiKey: string): void {
    const id: number = Number(this.route.snapshot.paramMap.get('id'));

    this.adresService.getAdres(id, xApiKey)
      .subscribe({
        next:
          response => {
            if (response.body) {
              this.adres = response.body;
            }
          },
        error: error => {
          this.errormessage = 'Status: ' + error.status + ' details: ' + error.error.detail;
        }
      });
  }
}
