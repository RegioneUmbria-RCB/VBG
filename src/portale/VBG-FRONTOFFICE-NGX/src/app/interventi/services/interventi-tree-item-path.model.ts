import { InterventiTreeItemModel } from "./interventi-tree-item.model";

export class InterventiTreeItemPathModel {
    id: string;
    descrizione: string;

    constructor(n: InterventiTreeItemModel) {
        this.id = n.id;
        this.descrizione = n.text;
    }
}
