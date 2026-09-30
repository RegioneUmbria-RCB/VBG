export class MenuRoot {

    private _root:JQuery;
    private _menuId:string;

    constructor(element:JQuery) {
        this._root = this.findRoot(element);
        this._menuId = this._root.data('menuId');

        if (this._menuId === '' || this._menuId == undefined) {
            console.error("Il menu root non ha un menu id impostato");
        }
    }

    private findRoot(element:JQuery):JQuery {

        var tmpEl = element,
            safetyCheck = 0;

        while(tmpEl != null && !tmpEl.hasClass('menu-root'))
        {
            if (safetyCheck > 100) {
                throw 'safetyCheck!!!!!';
            }

            tmpEl = tmpEl.parent();
            safetyCheck++;
        }

        if (tmpEl == null) {
            throw "Impossibile trovare un menu root";
        }

        return tmpEl;
    }

    public trigger(event: string, extraParameters?: any[]|Object): JQuery {
        return this._root.trigger(event, extraParameters);
    }

    public on(events: string, handler: (eventObject: JQueryEventObject, ...args: any[]) => any): JQuery {
        return this._root.on(events,handler);
    }

    public getMenuId():string {
        return this._menuId;
    }
}