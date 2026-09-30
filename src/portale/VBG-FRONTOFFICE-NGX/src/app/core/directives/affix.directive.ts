import { ScrollDispatcher } from "@angular/cdk/scrolling";
import { Directive, ElementRef } from "@angular/core";

@Directive({
    selector: "[appAffix]",
    standalone: true,
})
export class AffixDirective {
    private oldScrollPoint = -1;
    private started = false;

    private oldStatus = {
        position: "",
        top: "",
        width: "",
    };

    constructor(
        private el: ElementRef<HTMLElement>,
        private scrollDispatcher: ScrollDispatcher
    ) {
        this.scrollDispatcher
            .ancestorScrolled(el)
            .subscribe((/*x: CdkScrollable*/) => {
                let bodyRect =
                    el.nativeElement.ownerDocument.body.getBoundingClientRect();
                let elemRect = el.nativeElement.getBoundingClientRect();

                if (this.started && bodyRect.top > this.oldScrollPoint) {
                    // console.log('reset affix');

                    el.nativeElement.style.position = this.oldStatus.position;
                    el.nativeElement.style.top = this.oldStatus.top;
                    el.nativeElement.style.width = this.oldStatus.width;

                    this.started = false;

                    bodyRect =
                        el.nativeElement.ownerDocument.body.getBoundingClientRect();
                    elemRect = el.nativeElement.getBoundingClientRect();
                }

                if (elemRect.top < 0) {
                    // console.log('affix');
                    this.started = true;
                    this.oldScrollPoint = bodyRect.top;

                    this.oldStatus = {
                        position: el.nativeElement.style.position,
                        top: el.nativeElement.style.top,
                        width: el.nativeElement.style.width,
                    };

                    el.nativeElement.style.position = "fixed";
                    el.nativeElement.style.top = "0";
                    el.nativeElement.style.width = elemRect.width.toString() + "px";
                    // el.nativeElement.style. = 0;
                }

                // console.log('element-top', elemRect.top, 'document-top', bodyRect.top);
                // console.log('bodyRect.top', bodyRect.top,  'this.oldScrollPoint', this.oldScrollPoint);
            });
    }
}
