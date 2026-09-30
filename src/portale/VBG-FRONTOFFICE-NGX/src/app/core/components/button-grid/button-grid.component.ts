import { Component, OnInit, Input } from "@angular/core";
import { ButtonGridItem } from "./button-grid-item.model";
import { UrlLocaliService } from "../../services/url";
import { RouterLink } from "@angular/router";
import { NgClass } from "@angular/common";

@Component({
    selector: "app-button-grid",
    templateUrl: "./button-grid.component.html",
    standalone: true,
    imports: [
    NgClass,
    RouterLink
],
})
export class ButtonGridComponent implements OnInit {
    @Input() dataSource: ButtonGridItem[] = [];
    @Input() redirUrl: string;
    @Input() gridSize = 3;
    @Input() fillCells = false;

    gridClass = {};

    constructor(public urlService: UrlLocaliService) { }

    ngOnInit(): void {
        this.gridClass = {
            "grid-size-3": this.gridSize === 3,
            "grid-size-2": this.gridSize === 2,
            "fill-cells": this.fillCells,
        };
    }
}
