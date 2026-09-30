/// <reference path="../../../typings/index.d.ts" />
import * as EventNames from './../event-names';
import {RimuoviPreferiti} from './rimuovi-preferiti';
import {VoceMenu} from './../voce-menu';
import {MenuRoot} from './../menu-root';

export class PannelloPreferiti {

    _pannelloNoPreferiti:JQuery;
    _ul:JQuery;
    _nomiMenu = new Array<string>();
    _originalHeight:string;
    _menuName:string;
    _menuRoot:MenuRoot;
    
    constructor(protected _panel:JQuery) {

        this._ul = this._panel.find('.scelta-software');
        this._menuName = this._panel.data('menuName');

        this._pannelloNoPreferiti = _panel.find('.no-preferiti');
        this._originalHeight = this._pannelloNoPreferiti.css('height');

        this._menuRoot = new MenuRoot(_panel);

        this._pannelloNoPreferiti.hide();

        this._menuRoot.on(EventNames.RimuoviDaPreferiti, (e, softwareId, element) => {
            this.updateStatus();
        });

        this._menuRoot.on(EventNames.AggiungiAPreferiti, (e, softwareId:string, softwareName:string, menuName:string) => {

            if (this._menuName != menuName) {
                return;
            }

            var esiste =this._nomiMenu.filter( (x) => {
                return x.localeCompare(softwareName) === 0;
            }).length > 0; 

            if (esiste) {
                return;
            }

            let template = '<li class="voce-menu">' + 
                            '<i class="glyphicon glyphicon-star rimuovi-preferiti"></i>' + 
                            '<a href="#" class="seleziona-software" data-panel-id="' + softwareId +'">template</a>' + 
                           '</li>';
            let compiled = jQuery(template);
            
            this._ul.append(compiled);

            let newEl = new RimuoviPreferiti(compiled.find('i'));

            newEl.setSoftwareId(softwareId);
            newEl.setText(softwareName);

            var voceMenu = new VoceMenu(compiled);                                       
                                   
            

            this.updateStatus();
        });

        // fix per il link expand-menu che viene aggiunto da bootstrap
        this.fixExpandMenu();
        
        this.updateStatus();        
    }

    private fixExpandMenu():void {
        
        var expand = this._panel.find('li>a.espandi-menu');

        expand.each( (idx:number, elem:Element) => {
            var jqEl = jQuery(elem),
                child = jqEl.children();

            child.detach();
            jqEl.parent().prepend(child);
            jqEl.remove();
        });
    }

    private updateStatus() {
        this.leggiNomiVoci();
        this.visualizzaPannelloNoPreferiti();
    }

    private leggiNomiVoci() {
        this._nomiMenu = new Array<string>();

        this._panel.find('.voce-menu').each((idx, el) => {
            this._nomiMenu.push(jQuery(el).find('a').text().trim());
        }); 
    }

    private visualizzaPannelloNoPreferiti() {
        var esistonoPreferiti = this._panel.find('.voce-menu').length > 0;

        this._pannelloNoPreferiti.toggle(!esistonoPreferiti);
    }
}