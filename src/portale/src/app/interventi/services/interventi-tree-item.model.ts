import { InterventiTreeItemPathModel } from "./interventi-tree-item-path.model";

export class InterventiTreeItemModel {
    hasChilds: boolean;
    id: string;
    text: string;
    percorso: InterventiTreeItemPathModel[]
}
