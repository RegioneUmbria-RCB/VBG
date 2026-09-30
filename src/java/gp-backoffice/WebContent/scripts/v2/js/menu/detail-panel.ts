/// <reference path="../../typings/index.d.ts" />

export class DetailPanel
{
    constructor(private _panel:JQuery) {
    }

    public hide():void {
        this._panel.hide();
    }

    public show():void {
        this._panel.show();
    }

    public scrollTop():void {
        this._panel.scrollTop(0);
    }
}