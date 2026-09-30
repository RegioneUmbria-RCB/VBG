/// <reference path="../../typings/index.d.ts" />

export class GruppoMenu
{
    constructor(private _panel:JQuery) {
        var funzione = this._panel.find('.espandi-menu');

        funzione.on('click', (e) => {
            let el = jQuery(e.currentTarget),
                targetId = this._panel.data('toggle'),
                toggle = this._panel.parent().find(targetId),
                hidden = toggle.hasClass('collapse'),
                icon = el.find('.glyphicon');

            toggle.toggleClass('collapse', !hidden);

            icon.removeClass('glyphicon-menu-down');
            icon.removeClass('glyphicon-menu-up');

            icon.addClass(hidden ? 'glyphicon-menu-up': 'glyphicon-menu-down');

            e.preventDefault();
            return false;
        });
    }
}