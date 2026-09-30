/// <reference path="../../../typings/index.d.ts" />
import {AggiungiPreferiti} from './aggiungi-preferiti';
import * as EventNames from './../event-names';
import {MenuRoot} from './../menu-root';

export class RimuoviPreferiti extends AggiungiPreferiti {

    _menuRoot:MenuRoot;

    constructor(panel:JQuery) {
        super(panel);

        this._menuRoot = new MenuRoot(panel);
    }

    protected onMouseOver() {
        this._panel.removeClass('glyphicon-star');
        this._panel.addClass('glyphicon-star-empty'); 
    }

    protected onMouseOut() {
        this._panel.removeClass('glyphicon-star-empty');
        this._panel.addClass('glyphicon-star');        
    }

    protected onClick() {

        var parent = this.findContainer();

        parent.animate({height:'0px', padding:'0px'}, 300, () => {
            parent.remove();
            this._menuRoot.trigger(EventNames.RimuoviDaPreferiti, [this._softwareId, parent]);    
        });        
    }

    public setSoftwareId(value:string) {
        this._softwareId = value;
        this._panel.data('softwareId', value);
        this.findContainer().find('a').data('softwareId', value);
    }

    public setText(value:string) {
        this.findContainer().find('a').text(value);
    }

}