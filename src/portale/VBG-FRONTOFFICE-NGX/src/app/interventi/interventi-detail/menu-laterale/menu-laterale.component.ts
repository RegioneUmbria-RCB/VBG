import { ViewportScroller, NgClass } from "@angular/common";
import {
    Component,
    Input,
} from "@angular/core";
import { SlugifyPipe } from "@core/pipes";
import { SlugifyPipe as SlugifyPipe_1 } from "../../../core/pipes/slugify.pipe";

@Component({
    selector: "app-menu-laterale",
    templateUrl: "./menu-laterale.component.html",
    standalone: true,
    imports: [
    NgClass,
    SlugifyPipe_1
],
})
export class MenuLateraleComponent {
    @Input() public dataSource = new Array<string>();
    @Input() public sezioneAttiva = "";
    // @ViewChild(CdkScrollable) public scrollContainer: CdkScrollable;

    constructor(
        private slugifyPipe: SlugifyPipe,
        private viewportScroller: ViewportScroller
    ) { }

    scrollTo(titoloSezione: string): void {
        this.viewportScroller.scrollToAnchor(
            this.slugifyPipe.transform(titoloSezione)
        );
        // console.log(this.slugifyPipe.transform(titoloSezione));
    }
}
