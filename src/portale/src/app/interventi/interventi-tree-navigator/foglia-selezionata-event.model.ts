import { InterventiTreeItemModel} from '../services';
import { InterventiTreeItemPathModel } from '../services/interventi-tree-item-path.model';

export class FogliaSelezionataEventModel {
    constructor(public node: InterventiTreeItemModel, public lastNode: InterventiTreeItemPathModel) {
    }
}
