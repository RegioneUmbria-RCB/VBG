/// <reference path="../../../typings/index.d.ts" />
import * as EventNames from './../event-names';
import {MenuRoot} from './../menu-root';

export class AggiungiPreferiti {

    _softwareId:string = ''; 
    _menuRoot:MenuRoot;

    constructor(protected _panel:JQuery) {

        this._softwareId = this._panel.data('softwareId');
        this._menuRoot = new MenuRoot(_panel);

        _panel.on('mouseover', (e) => {
            this.onMouseOver();
        });

        _panel.on('mouseout', (e) => {
            this.onMouseOut();
        });

        _panel.on('click', (e) => {
            this.onClick();
        });
    }



    protected onMouseOver() {
        this._panel.removeClass('glyphicon-star-empty');
        this._panel.addClass('glyphicon-star');
    }

    protected onMouseOut() {
        this._panel.removeClass('glyphicon-star');
        this._panel.addClass('glyphicon-star-empty');        
    }

    protected onClick() {
        var text = this.findContainer().find('a').text().trim(),
            menuName = this._panel.data('menuName');

        this._menuRoot.trigger(EventNames.AggiungiAPreferiti, [this._softwareId, text, menuName]);
    }

    protected findContainer():JQuery {
        var parent = this._panel.parent();

        while(parent.length > 0 && parent[0].tagName.toUpperCase() != "LI") {
            parent = parent.parent();
        } 

        return parent;
    }    
}

//aggiungi-preferiti