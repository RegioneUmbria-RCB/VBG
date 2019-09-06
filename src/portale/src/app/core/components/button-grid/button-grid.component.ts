import { Component, OnInit, Input } from '@angular/core';
import { ButtonGridItem } from './button-grid-item.model';
import { UrlLocaliService } from '../../services/url';

@Component({
    selector: 'app-button-grid',
    templateUrl: './button-grid.component.html'
})
export class ButtonGridComponent implements OnInit {

    @Input('dataSource') dataSource: ButtonGridItem[] = [];
    @Input('redirUrl') redirUrl: string;
    @Input() gridSize = 3;
    @Input() fillCells = false;

    gridClass = {};

    constructor(public urlService: UrlLocaliService) { }

    ngOnInit() {
        this.gridClass = {
            'grid-size-3': this.gridSize === 3,
            'grid-size-2': this.gridSize === 2,
            'fill-cells': this.fillCells
        };
    }

}
