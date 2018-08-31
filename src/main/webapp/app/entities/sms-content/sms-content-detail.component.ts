import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISmsContent } from 'app/shared/model/sms-content.model';

@Component({
    selector: 'jhi-sms-content-detail',
    templateUrl: './sms-content-detail.component.html'
})
export class SmsContentDetailComponent implements OnInit {
    smsContent: ISmsContent;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ smsContent }) => {
            this.smsContent = smsContent;
        });
    }

    previousState() {
        window.history.back();
    }
}
