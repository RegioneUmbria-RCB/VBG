/// <reference path="../../typings/index.d.ts" />
import {GruppoMenu} from './gruppo-menu';
import {CasellaRicercaTestuale} from './casella-ricerca-testuale';
import {PannelloPreferiti} from './preferiti/pannello-preferiti';
import {IApriPannelloEventArgs} from './apri-pannello-event-args';
import {VoceMenu} from './voce-menu';
import {MenuRoot} from './menu-root';
import * as EventNames from './event-names';

export class LeftPanel
{
    private _ricercatestuale:CasellaRicercaTestuale;
    private _funzioniMenu = new Array<GruppoMenu>();
    private _vociMenu = new Array<VoceMenu>();
    private _pannelloPreferiti:PannelloPreferiti;
    private _menuRoot:MenuRoot;
    

    private _lastSoftwarePanel:JQuery = null;

    constructor(private _panel:JQuery) {
        this._menuRoot = new MenuRoot(_panel);
        this._ricercatestuale = new CasellaRicercaTestuale(this._menuRoot.getMenuId(), this._panel.find('.ricerca-testuale'));
        this._pannelloPreferiti = new PannelloPreferiti(this._panel.find('.pannello-preferiti'));

        this._panel.find('.gruppo-menu').each((idx, el) => {
            var jqEl = jQuery(el),
                fn = new GruppoMenu(jqEl);

            this._funzioniMenu.push(fn);
        });

        this._panel.find('.voce-menu').each((idx, el) => {
            var jqEl = jQuery(el),
                selezionaSoftware = new VoceMenu(jqEl);
            
            this._vociMenu.push(selezionaSoftware);
        });

        this._menuRoot.on(EventNames.PannelloAperto, (e, args:IApriPannelloEventArgs) => {
            if (this._lastSoftwarePanel != null) {
                this._lastSoftwarePanel.removeClass('menu-selected');
            }

            this._lastSoftwarePanel = args.element;
            this._lastSoftwarePanel.addClass('menu-selected');
        });

        this._menuRoot.on(EventNames.ApriRicercaTestuale, (e) => {
            if (this._lastSoftwarePanel != null) {
                this._lastSoftwarePanel.removeClass('menu-selected');
            }

            this._lastSoftwarePanel = null;
        });    

        this._panel.on('click', (e) => {
            e.preventDefault();
            e.stopPropagation();
        });  
    }
}
