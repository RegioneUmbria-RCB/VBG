
import { filter, map } from 'rxjs/operators';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';



import { UrlLocaliService, RisorseService } from '../../core/services';
import { InterventiLocaliService } from '../services';
import { InterventiTreeNavigatorComponent } from '../interventi-tree-navigator/interventi-tree-navigator.component';

@Component({
    selector: 'app-interventi-locali',
    templateUrl: './interventi-locali.component.html'
})
export class InterventiLocaliComponent implements OnInit {

    caricamentoCompletato = true;

    @ViewChild(InterventiTreeNavigatorComponent, { static: true }) treeNavigator: InterventiTreeNavigatorComponent;
    titoloPagina: string;

    constructor(private service: InterventiLocaliService, private router: Router,
        private route: ActivatedRoute, private urlLocaliService: UrlLocaliService,
        private risorseService: RisorseService) { }

    ngOnInit() {

        this.titoloPagina = this.risorseService.getRisorsa('interventi.lista.titoloPagina', 'Procedimenti, modulistica ed adempimenti');

        this.treeNavigator.service = this.service;

        this.route.queryParams.pipe(
            map(params => params['open']),
            filter(val => val))
            .subscribe(open => this.treeNavigator.ripristinaGerarchia(open));

        this.treeNavigator.ripristinaGerarchia('-1');
    }

    statoCaricamento(val: boolean) {
        this.caricamentoCompletato = val;
    }


    onFogliaSelezionata($event: any) {
        console.log($event);

        const url = this.urlLocaliService.url('/interventi-locali', [$event.node.id]);

        this.router.navigate([url], {
            queryParams: {
                returnTo: $event.lastNode.id
            }
        });

    }
}
