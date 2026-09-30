import { Component, Input, Output, EventEmitter } from "@angular/core";
import { NormativaModel } from "./normativa.model";


@Component({
    selector: "app-griglia-normativa",
    templateUrl: "./griglia-normativa.component.html",
    standalone: true,
    imports: [],
})
export class GrigliaNormativaComponent {
    @Input() dataSource: NormativaModel[];
    @Output() download = new EventEmitter<string>();



    onDownload($event: string): void {
        this.download.emit($event);
    }
}
