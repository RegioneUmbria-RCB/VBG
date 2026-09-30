import { Component, Input } from "@angular/core";


@Component({
    selector: "app-sezione",
    templateUrl: "./sezione.component.html",
    standalone: true,
    imports: [],
})
export class SezioneComponent {
    @Input() titolo: string;
    @Input() corpo: string;

}
