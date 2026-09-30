import { Component, Input } from "@angular/core";
import { SlugifyPipe } from "../../../core/pipes/slugify.pipe";


@Component({
    selector: "app-sezione-intervento",
    templateUrl: "./sezione-intervento.component.html",
    styleUrls: ["./sezione-intervento.component.less"],
    standalone: true,
    imports: [SlugifyPipe],
})
export class SezioneInterventoComponent {
    @Input() public titolo = "titolo sezione";
    @Input() public visibileSe = true;

}
