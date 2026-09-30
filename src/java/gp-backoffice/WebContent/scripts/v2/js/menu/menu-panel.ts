/// <reference path="../../typings/index.d.ts" />
import {LeftPanel} from './left-panel';
import {RightPanel} from './right-panel';

export class MenuPanel {

    private _leftPanel:LeftPanel;
    private _rightPanel:RightPanel;

    constructor(private _panel:JQuery) {
        var rightPanel = _panel.find('.right-panel');

        this._leftPanel = new LeftPanel(_panel.find('.left-panel'));
        this._rightPanel = new RightPanel(rightPanel);
    }
}
