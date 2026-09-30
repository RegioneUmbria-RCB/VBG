/// <reference path="../../typings/index.d.ts" />
import * as EventNames from './event-names';
import {IRicercaTestoEventArgs} from './ricerca-testo-event-args';
import {MenuRoot} from './menu-root';

interface IDizionarioVociItem
{
    testo:string;
    testoLowerCase:string;
    link:string;
    software:string; 
}

export class DizionarioRicercatestuale{

    private _dizionarioVoci = Array<IDizionarioVociItem>();
    private _menuRoot:MenuRoot;

    constructor(panel:JQuery) {

        this._menuRoot = new MenuRoot(panel);

        panel.find('.ricercabile').each((idx, el) => {
            var jqEl = jQuery(el),
                titoloSezione = jqEl.find('h1').text(),
                voci = jqEl.find('.voce-dizionario');

            if (titoloSezione.indexOf(' / ') > 0) {
                titoloSezione = titoloSezione.slice(titoloSezione.indexOf(' / ') + 3);
            }

            voci.each((idx2, voce) =>{
                var a = jQuery(voce),
                    sezioneAppartenenza = a.parent().parent().parent().find('>h2').text(),
                    testo = sezioneAppartenenza + ' / ' + a.text(),
                    link = a.attr('href');

                this._dizionarioVoci.push({
                    testo: testo,
                    testoLowerCase: testo.toLocaleLowerCase(),
                    link: link,
                    software: titoloSezione 
                }); 
            });
        });

        this._menuRoot.on(EventNames.RicercaTesto, (e:JQueryEventObject, args:IRicercaTestoEventArgs) => {

            this.ricercaTesto(args.text);
        });
    }

    private ricercaTesto(match:string):void {
        var results:Array<IDizionarioVociItem>,
            dictResult:any = null,
            lowerText = match.toLocaleLowerCase(),
            rVal = null;

        results = this._dizionarioVoci.filter((item) => {
            return item.testoLowerCase.indexOf(lowerText) > -1;
        });

        if (results.length > 0) {

            dictResult = {};

            for(var item of results) {
                if (dictResult[item.software] == null) {
                    dictResult[item.software] = new Array();
                }

                dictResult[item.software].push(item);
            }
            
            rVal =  {items:[]};

            for( var software in dictResult) {
                rVal.items.push({
                    titolo: software,
                    voci: dictResult[software]
                });
            }

            this._menuRoot.trigger(EventNames.TestoCercatoTrovato, rVal);

            return;     
        }

        this._menuRoot.trigger(EventNames.TestoCercatoNonTrovato, rVal);

    }
}