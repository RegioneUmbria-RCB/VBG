/// <reference path="../scripts/typings/jquery/jquery.d.ts" />
import $ = require('jquery');

class CategoriaModulistica {

    private figliVisibili: number;

    constructor(private jqueryElement: JQuery, private numeroFigli:number) {
        this.figliVisibili = numeroFigli;
    }

    verificaVisibilita():void {

        if (this.figliVisibili > 0) {
            this.jqueryElement.removeClass('collapsed');
            this.jqueryElement.addClass('expanded');
        } else {
            this.jqueryElement.addClass('collapsed');
            this.jqueryElement.removeClass('expanded');
        }

        this.jqueryElement.toggle(this.figliVisibili > 0);
    }

    figlioNascosto ():void {
        this.figliVisibili--;

        this.verificaVisibilita();
    }

    figlioVisibile():void {
        this.figliVisibili++;

        this.verificaVisibilita();
    }

}


class ElementoModulistica {

    private titolo: string;
    private descrizione: string;
    private visibile:boolean = true;

    constructor(private categoria: any, private jqueryElement: JQuery, titolo:string, descrizione:string) {
        this.titolo = titolo.toUpperCase();
        this.descrizione = descrizione.toUpperCase();
    }
    


    contiene(testo:string):boolean {
        var ucase = testo.toUpperCase();

        if (ucase === '') {
            return true;
        }

        return this.titolo.indexOf(ucase) > 0 || this.descrizione.indexOf(ucase) > 0;
    };

    mostra(mostra:boolean):void {
        this.jqueryElement.toggle(mostra);

        if (mostra && !this.visibile) {
            this.categoria.figlioVisibile();
        }
        if (!mostra && this.visibile) {
            this.categoria.figlioNascosto();
        }

        this.visibile = mostra;
    };
}



export class RicercaModulistica {
    private datiModulistica = new Array<ElementoModulistica>();
    private categorieModulistica = new Array<CategoriaModulistica>();



    filtraValori(testo:string):void {

        testo = testo.toUpperCase();

        var valoriTrovati = this.datiModulistica.map((item) => item.contiene(testo));

        this.datiModulistica.forEach((item, idx) => item.mostra(valoriTrovati[idx]));

        $('#nessun-risultato').toggle(valoriTrovati.filter(function (x) { return x; }).length === 0);

        if (testo === '') {
            this.collapseAll();
        }
    }

    handleTextSearch() {
        var textbox = $('#ricercaTestuale'),
            categorieNode = $('.categoria-modulistica');

        categorieNode.each((idx, item) => {

            var elCat = $(item),
                children = elCat.find('.dati-modulistica'),
                cat = new CategoriaModulistica(elCat, children.length);

            this.categorieModulistica.push(cat);

            children.each((idx, dati) => {
                var el = $(dati),
                    modulistica = new ElementoModulistica(cat, el, el.find('>h3').text(), el.find('>div').text());

                this.datiModulistica.push(modulistica);
            });
        });

        textbox.on('keyup', () => this.filtraValori(textbox.val()));
    }



    expandOrCollapse(element:any):void {
        var section = element.parent();

        if (section.hasClass('collapsed')) {
            this.expand(section);
        } else {
            this.collapse(section);
        }
    }

    private collapse(section:any):void {
        section.addClass('collapsed');
        section.removeClass('expanded');
    }

    private expand(section:any):void {
        section.removeClass('collapsed');
        section.addClass('expanded');
    }

    collapseAll():void {
        $('.categoria-modulistica').removeClass('expanded');
        $('.categoria-modulistica').addClass('collapsed');
    }

    collegaHandlerCollapse():void {
        $('.categoria-modulistica>h2').on('click', (e) => {
            this.expandOrCollapse($(e.target));

            e.preventDefault();
        });

        $('.categoria-modulistica>h2>i').on('click', (e) => {
            this.expandOrCollapse($(e.target).parent());

            e.preventDefault();
        });
    }
}