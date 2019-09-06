import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UrlLocaliService, RisorseService } from '../../core/services';
import { InterventiLocaliService, InterventoTopModel } from '../../interventi';
import { ProcedimentiLocaliService, ProcedimentoTopModel } from '../../procedimenti';


@Component({
    selector: 'app-home-in-primo-piano',
    templateUrl: './home-in-primo-piano.component.html',
    styleUrls: ['./home-in-primo-piano.component.less']
})
export class HomeInPrimoPianoComponent implements OnInit {

    interventiTop$: Observable<InterventoTopModel[]>;
    procedimentiTop$: Observable<ProcedimentoTopModel[]>;
    urlDettagliIntervento: string;
    urlDettagliProcedimento: string;
    titoloAttivita: string;
    mostraInterventiRegionali = true;

    mostraInterventiTop = false;
    mostraProcedimentiTop = false;


    constructor(private interventiService: InterventiLocaliService,
        private procedimentiService: ProcedimentiLocaliService,
        urlLocaliService: UrlLocaliService,
        private risorseService: RisorseService) {

        this.urlDettagliIntervento = urlLocaliService.url('/interventi-locali');
        this.urlDettagliProcedimento = urlLocaliService.url('/procedimenti');
    }

    ngOnInit() {
        this.interventiTop$ = this.interventiService.getTop();
        this.procedimentiTop$ = this.procedimentiService.getTop();


        this.interventiTop$.subscribe(x => {
            this.mostraInterventiTop = x && x.length > 0;
        });

        this.procedimentiTop$.subscribe(x => {
            this.mostraProcedimentiTop = x && x.length > 0;
        });

        this.titoloAttivita = this.risorseService.getRisorsa('home.inPrimoPiano.attivita', 'Attività');
        this.mostraInterventiRegionali = (this.risorseService.getRisorsa('home.inPrimoPiano.usaInterventiRegionali', 'true') === 'true');
    }

}
