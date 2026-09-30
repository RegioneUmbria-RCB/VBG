/// <reference path="../../typings/index.d.ts" />
import * as EventNames from './event-names';
import {MenuRoot} from './menu-root';

export class SelezionaSoftware {
    _panelId: string;
    _parent: JQuery;
    _rootElement: JQuery;
    _menuRoot: MenuRoot;
    _menuId: string = ''; 

    constructor(private _panel: JQuery) {

        this._panelId = _panel.data('panelId');
        this._parent = this._panel.parent()
        this._menuRoot = new MenuRoot(_panel);

        _panel.on("mouseover click", (e) => {

            this._menuRoot.trigger(EventNames.ApriPannello, [{
                panelId: this._panelId,
                menuId: this._menuId,
                element: this._parent
            }]);

        });
    }
}