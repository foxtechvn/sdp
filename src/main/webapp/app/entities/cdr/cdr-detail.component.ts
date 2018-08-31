import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICdr } from 'app/shared/model/cdr.model';

@Component({
    selector: 'jhi-cdr-detail',
    templateUrl: './cdr-detail.component.html'
})
export class CdrDetailComponent implements OnInit {
    cdr: ICdr;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cdr }) => {
            this.cdr = cdr;
        });
    }

    previousState() {
        window.history.back();
    }
}
