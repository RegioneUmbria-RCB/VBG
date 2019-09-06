
import { forkJoin as observableForkJoin, Observable, Subject } from 'rxjs';

import { concatMap, filter, debounceTime } from 'rxjs/operators';
import { Component, OnInit, ViewChild, ElementRef, Input } from '@angular/core';
import { InterventiLocaliService, InterventiRegionaliService } from '../services';
import { UrlLocaliService, RisorseService } from '../../core/services';

@Component({
    selector: 'app-ricerca-interventi',
    templateUrl: './ricerca-interventi.component.html'
})
export class RicercaInterventiComponent implements OnInit {

    public segnapostoTesto: string;
    public dataSource: Subject<string>;
    public text: string;
    public matches: any[] = [];
    public totalmatches: 0;
    public loaded = true;

    @ViewChild('searchInput', { static: false })
    inputRef: ElementRef;

    @Input() mostraInterventiLocali = true;
    @Input() mostraInterventiRegionali = true;
    @Input() mostraTitoliSezioni = true;

    urlRedirectLocale: string;
    urlRedirectRegionale: string;

    public constructor(private interventiLocali: InterventiLocaliService,
        private interventiRegionali: InterventiRegionaliService,
        private urlLocaliService: UrlLocaliService,
        private risorseService: RisorseService) {

        this.dataSource = new Subject<string>();

        this.dataSource.pipe(
            debounceTime(250),
            filter(x => {
                return x && x.length >= 3;
            }),
            concatMap(term => observableForkJoin(this.interventiLocali.cerca(term), this.interventiRegionali.cerca(term))))
            .subscribe(val => {
                this.loaded = true;
                this.matches = [];

                if (val[0] && this.mostraInterventiLocali) {
                    (<any>val[0]).urlRedirect = this.urlRedirectLocale;
                    this.matches.push(val[0]);
                }

                if (val[1] && this.mostraInterventiRegionali) {
                    (<any>val[1]).urlRedirect = this.urlRedirectRegionale;
                    this.matches.push(val[1]);
                }

                this.totalmatches = 0;
                this.matches.forEach(value => this.totalmatches += value.length || 0);
            }, err => console.error(err));

    }

    chiudiRicerca() {
        this.clearControl();
    }

    clearControl() {
        this.dataSource.next('');
        this.text = '';
    }

    ngOnInit(): void {
        this.urlRedirectLocale = this.urlLocaliService.url('/interventi-locali');
        this.urlRedirectRegionale = this.urlLocaliService.url('/interventi-regionali');

        // tslint:disable-next-line: max-line-length
        this.segnapostoTesto = this.risorseService.getRisorsa('ricercaInterventiComponent.segnapostoTesto', 'Cerca attività e procedimenti');
    }

    onKeyUp(testo: string, $event: KeyboardEvent) {

        if ($event.which === 27) {
            this.clearControl();
        } else {
            this.dataSource.next(testo);
        }
    }
}
