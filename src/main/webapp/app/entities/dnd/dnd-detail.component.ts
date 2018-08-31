import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDnd } from 'app/shared/model/dnd.model';

@Component({
    selector: 'jhi-dnd-detail',
    templateUrl: './dnd-detail.component.html'
})
export class DndDetailComponent implements OnInit {
    dnd: IDnd;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dnd }) => {
            this.dnd = dnd;
        });
    }

    previousState() {
        window.history.back();
    }
}
