import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISmsContent } from 'app/shared/model/sms-content.model';
import { SmsContentService } from './sms-content.service';

@Component({
    selector: 'jhi-sms-content-update',
    templateUrl: './sms-content-update.component.html'
})
export class SmsContentUpdateComponent implements OnInit {
    private _smsContent: ISmsContent;
    isSaving: boolean;
    startAt: string;
    expiredAt: string;

    constructor(private smsContentService: SmsContentService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ smsContent }) => {
            this.smsContent = smsContent;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.smsContent.startAt = moment(this.startAt, DATE_TIME_FORMAT);
        this.smsContent.expiredAt = moment(this.expiredAt, DATE_TIME_FORMAT);
        if (this.smsContent.id !== undefined) {
            this.subscribeToSaveResponse(this.smsContentService.update(this.smsContent));
        } else {
            this.subscribeToSaveResponse(this.smsContentService.create(this.smsContent));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISmsContent>>) {
        result.subscribe((res: HttpResponse<ISmsContent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get smsContent() {
        return this._smsContent;
    }

    set smsContent(smsContent: ISmsContent) {
        this._smsContent = smsContent;
        this.startAt = moment(smsContent.startAt).format(DATE_TIME_FORMAT);
        this.expiredAt = moment(smsContent.expiredAt).format(DATE_TIME_FORMAT);
    }
}
