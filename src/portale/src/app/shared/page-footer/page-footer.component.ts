import { Component, OnInit } from '@angular/core';

import { DatiComuneService, DatiComuneModel } from '../../dati-comune';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-page-footer',
    templateUrl: './page-footer.component.html'
})
export class PageFooterComponent implements OnInit {

    datiComune: DatiComuneModel;
    loaded = false;

    constructor(private datiComuneService: DatiComuneService) {

    }

    ngOnInit() {
        this.datiComuneService.get().subscribe( x => {
            this.datiComune = x;
            this.loaded = true;
        });
    }
}
