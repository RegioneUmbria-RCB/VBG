import { Component, Input } from "@angular/core";
import { OnereModel } from "./onere.model";


@Component({
    selector: "app-griglia-oneri",
    templateUrl: "./griglia-oneri.component.html",
    standalone: true,
    imports: [],
})
export class GrigliaOneriComponent {
    @Input() dataSource: OnereModel[];
}
