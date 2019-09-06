import { InterventiTreeItemModel } from "./interventi-tree-item.model";

export class InterventiTreeItemPathModel {

    constructor(n:InterventiTreeItemModel){
        this.id = n.id;
        this.descrizione = n.text;
    }
    id: string;
    descrizione: string;
}