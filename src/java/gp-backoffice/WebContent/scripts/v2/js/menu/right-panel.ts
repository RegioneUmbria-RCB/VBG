/// <reference path="../../typings/index.d.ts" />
import {DetailPanel} from './detail-panel';
import {IApriPannelloEventArgs} from './apri-pannello-event-args';
import {DizionarioRicercatestuale} from './dizionario-ricerca-testuale';
import {PannelloRicercaTestuale} from './pannello-ricerca-testuale';
import {MenuRoot} from './menu-root';

import * as EventNames from './event-names';

export class RightPanel
{
    private _detailPanels: {[id:string]:DetailPanel;} = {};
    private _keysList = new Array<string>();
    private _cambiamentoPannelloBloccato = false;
    private _ricercaTestuale:DizionarioRicercatestuale;
    private _pannelloricercatestuale:PannelloRicercaTestuale;
    private _menuRoot:MenuRoot;
    

    constructor(private _panel:JQuery) {
        this._menuRoot = new MenuRoot(this._panel);        

        this._panel.find('.pannello-menu').each((idx, el) => {
            var jqEl = jQuery(el),
                pnlId = jqEl.data('panelId');

            this._keysList.push(pnlId);
            this._detailPanels[pnlId] = new DetailPanel(jqEl);
        });

        this._ricercaTestuale = new DizionarioRicercatestuale(this._panel);
        this._pannelloricercatestuale = new PannelloRicercaTestuale(_panel.find('.MENU_RICERCA'));

        this.showPanel("MENU_RICERCA");        

        this._menuRoot.on(EventNames.ApriPannello, (e, args:IApriPannelloEventArgs) => {

            if (this.showPanel(args.panelId))
            {
                this._menuRoot.trigger(EventNames.PannelloAperto, [args]);
            }
        });

        this._menuRoot.on(EventNames.BloccaCambiamentoPannello, (e) => {
            this._cambiamentoPannelloBloccato = true;
        });

        this._menuRoot.on(EventNames.SbloccaCambiamentoPannello, (e) => {
            this._cambiamentoPannelloBloccato = false;
        });

        this._menuRoot.on(EventNames.ApriRicercaTestuale, (e) => {
            this.showPanel("MENU_RICERCA");
        });        
    }

    private hideAllPanels() {
        for (var key of this._keysList) {
            this._detailPanels[key].hide();
        }
    }

    private showPanel(panelId:string):boolean {

        if (this._cambiamentoPannelloBloccato) {
            return false;
        }

        this.hideAllPanels();
        this._detailPanels[panelId].scrollTop();
        this._detailPanels[panelId].show();

        return true;
    }
}
