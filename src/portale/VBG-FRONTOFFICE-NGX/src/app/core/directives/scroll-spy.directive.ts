import { ScrollDispatcher } from "@angular/cdk/scrolling";
import {
    Directive,
    Input,
    EventEmitter,
    Output,
    ElementRef,
    ChangeDetectorRef,
} from "@angular/core";

@Directive({
    selector: "[appScrollSpy]",
    standalone: true,
})
export class ScrollSpyDirective {
    @Input() public spiedTags: string;
    @Output() public sectionChange = new EventEmitter<string>();
    private currentSection: string;

    constructor(
        private _el: ElementRef<HTMLElement>,
        private scrollDispatcher: ScrollDispatcher,
        private changeDetector: ChangeDetectorRef
    ) {
        console.log("scroll init");

        this.scrollDispatcher
            .ancestorScrolled(_el)
            .subscribe((/*x: CdkScrollable*/) => {
                let currentSection: string;
                const children = this._el.nativeElement.querySelectorAll(
                    this.spiedTags
                );

                for (const el of Array.from(children)) {

                    const elemRect = el.getBoundingClientRect();

                    if (elemRect.top > 0) {
                        currentSection = el.id;
                        break;
                    }
                }

                if (currentSection && currentSection !== this.currentSection) {
                    this.currentSection = currentSection;
                    this.sectionChange.emit(this.currentSection);

                    changeDetector.detectChanges();
                }
            });
    }

    /*
    @HostListener('scroll', ['$event'])
    onScroll(event: any) {

        console.log('scroll');

        let currentSection: string;
        const children = this._el.nativeElement.children;
        const scrollTop = event.target.scrollTop;
        const parentOffset = event.target.offsetTop;
        for (let i = 0; i < children.length; i++) {
            const element = children[i];
            if (this.spiedTags.some(spiedTag => spiedTag === element.tagName)) {
                if ((element.offsetTop - parentOffset) <= scrollTop) {
                    currentSection = element.id;
                }
            }
        }
        if (currentSection !== this.currentSection) {
            this.currentSection = currentSection;
            this.sectionChange.emit(this.currentSection);
        }
    }
    */
}
