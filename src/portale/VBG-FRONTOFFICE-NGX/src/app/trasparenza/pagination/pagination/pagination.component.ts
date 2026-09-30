import { Component, Input, Output, EventEmitter } from "@angular/core";
import { PageChangedEvent } from "ngx-bootstrap/pagination/public_api";
import { FormsModule } from "@angular/forms";
import { PaginationModule } from "ngx-bootstrap/pagination";

@Component({
    selector: "app-pagination",
    templateUrl: "./pagination.component.html",
    standalone: true,
    imports: [PaginationModule, FormsModule],
})
export class PaginationComponent {
    @Input()
    public totalItems: number;
    @Input()
    public itemsPerPage: number;
    @Input()
    public maxSize: number;

    @Input()
    public currentPage: number;

    // public pagerConfig: IPagerConfig;
    // returnedArray: Obj[] = new Array();

    @Output()
    pageChanged = new EventEmitter<PageChangedEvent>();

    onPageChanged(event: PageChangedEvent): void {
        /*
        this.returnedArray = [];
        const firstResult = this.pagerService.getFirstResult(event.page, event.itemsPerPage);
        const maxResults = this.pagerConfig.itemsPerPageRicercaDefault;
        this.returnedArray.push(new Obj('firstResult', firstResult.toString()));
        this.returnedArray.push(new Obj('maxResults', maxResults.toString()));
        this.returnedArray.push(new Obj('page', event.page.toString()));

        this.pageChange.emit(this.returnedArray);
        */
        this.pageChanged.next(event);
    }
}
