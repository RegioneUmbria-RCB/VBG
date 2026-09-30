/// <reference path="../../typings/index.d.ts" />
import * as EventNames from './event-names';
import {MenuRoot} from './menu-root';

export class CasellaRicercaTestuale
{
    private _textbox:JQuery;
    private _menuRoot:MenuRoot;

    constructor(private _menuId:string, private _panel:JQuery) {
        
        this._menuRoot = new MenuRoot(this._panel);
        this._textbox = this._panel.find('input[type=text]');

        this._textbox.on('focus click', (e) => {

            this._menuRoot.trigger(EventNames.ApriRicercaTestuale);
            this._menuRoot.trigger(EventNames.BloccaCambiamentoPannello);

            this._panel.addClass('selected');

        });

        this._textbox.on('blur', (e) =>{
            this._menuRoot.trigger(EventNames.SbloccaCambiamentoPannello);

            this._panel.removeClass('selected');
        });

        this._textbox.on('input propertychange paste', (e) => {
            var text = this._textbox.val() as string;

            if (text.length > 2) {
                this._menuRoot.trigger(EventNames.RicercaTesto, {
                    text: text,
                    menuId: this._menuId
                });
            } else {
                this._menuRoot.trigger(EventNames.ResetRicercaTestuale);
            }
            
        });       

        this._panel.addClass('selected'); 
    }
}