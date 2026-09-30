/// <reference path="../../typings/index.d.ts" />
import {MenuPanel} from './menu-panel';
import {AggiungiPreferiti} from './preferiti/aggiungi-preferiti';

export interface IMenuOptions
{
    menuUrl:string,
    templateUrl:string,
    searchResultTemplateUrl:string
}

export class MenuBuilder2
{
    _bloccaPannelli = false;
    _menuTemplate: JsRender.Template = null;
    _menuPanels = new Array<MenuPanel>();
    _aggiungiPreferiti = new Array<AggiungiPreferiti>();

    constructor(private _rootElement:JQuery, private _options:IMenuOptions) {

    }

    private async loadTemplate(url:string):Promise<JsRender.Template> {
        var html = await jQuery.ajax({
            url: url,
            dataType: "html"
        });

        return jQuery.templates(html);
    }    

    public async build():Promise<void> {
        var data = await jQuery.ajax({
            url: this._options.menuUrl
        });

        this._menuTemplate = await this.loadTemplate(this._options.templateUrl);
        var searchResultTemplate = await this.loadTemplate(this._options.searchResultTemplateUrl);

        let html = this._menuTemplate.render(data);
        
        this._rootElement.append(html);


        // Associo a tutti i pannelli di ricerca testuale il template da utilizzare
        this._rootElement.find('.MENU_RICERCA').each((idx, elem) => {
            jQuery.data(elem, 'searchResultTemplate', searchResultTemplate);
        });

        // Inizializzo i pannelli
        this._rootElement.find('.dropdown').each((idx, el) => {
            this._menuPanels.push(new MenuPanel(jQuery(el)));
        });        

    }
}
