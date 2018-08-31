import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISms } from 'app/shared/model/sms.model';

@Component({
    selector: 'jhi-sms-detail',
    templateUrl: './sms-detail.component.html'
})
export class SmsDetailComponent implements OnInit {
    sms: ISms;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sms }) => {
            this.sms = sms;
        });
    }

    previousState() {
        window.history.back();
    }
}
