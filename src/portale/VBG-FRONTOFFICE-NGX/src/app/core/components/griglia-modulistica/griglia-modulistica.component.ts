import { Component, Input, Output, EventEmitter } from "@angular/core";
import { ModuloModel } from "./modulo.model";


@Component({
    selector: "app-griglia-modulistica",
    templateUrl: "./griglia-modulistica.component.html",
    standalone: true,
    imports: [],
})
export class GrigliaModulisticaComponent {
    @Input() dataSource: ModuloModel[];
    @Output() download = new EventEmitter<string>();

    public onDownload($event: string): void {
        console.log($event);
        this.download.emit($event);
    }
}
