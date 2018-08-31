import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISubscriber } from 'app/shared/model/subscriber.model';
import { SubscriberService } from './subscriber.service';

@Component({
    selector: 'jhi-subscriber-update',
    templateUrl: './subscriber-update.component.html'
})
export class SubscriberUpdateComponent implements OnInit {
    private _subscriber: ISubscriber;
    isSaving: boolean;
    joinAt: string;
    leftAt: string;
    expiryTime: string;
    chargeLastTime: string;
    chargeNextTime: string;
    chargeLastSuccess: string;
    notifyLastTime: string;

    constructor(private subscriberService: SubscriberService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ subscriber }) => {
            this.subscriber = subscriber;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.subscriber.joinAt = moment(this.joinAt, DATE_TIME_FORMAT);
        this.subscriber.leftAt = moment(this.leftAt, DATE_TIME_FORMAT);
        this.subscriber.expiryTime = moment(this.expiryTime, DATE_TIME_FORMAT);
        this.subscriber.chargeLastTime = moment(this.chargeLastTime, DATE_TIME_FORMAT);
        this.subscriber.chargeNextTime = moment(this.chargeNextTime, DATE_TIME_FORMAT);
        this.subscriber.chargeLastSuccess = moment(this.chargeLastSuccess, DATE_TIME_FORMAT);
        this.subscriber.notifyLastTime = moment(this.notifyLastTime, DATE_TIME_FORMAT);
        if (this.subscriber.id !== undefined) {
            this.subscribeToSaveResponse(this.subscriberService.update(this.subscriber));
        } else {
            this.subscribeToSaveResponse(this.subscriberService.create(this.subscriber));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISubscriber>>) {
        result.subscribe((res: HttpResponse<ISubscriber>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get subscriber() {
        return this._subscriber;
    }

    set subscriber(subscriber: ISubscriber) {
        this._subscriber = subscriber;
        this.joinAt = moment(subscriber.joinAt).format(DATE_TIME_FORMAT);
        this.leftAt = moment(subscriber.leftAt).format(DATE_TIME_FORMAT);
        this.expiryTime = moment(subscriber.expiryTime).format(DATE_TIME_FORMAT);
        this.chargeLastTime = moment(subscriber.chargeLastTime).format(DATE_TIME_FORMAT);
        this.chargeNextTime = moment(subscriber.chargeNextTime).format(DATE_TIME_FORMAT);
        this.chargeLastSuccess = moment(subscriber.chargeLastSuccess).format(DATE_TIME_FORMAT);
        this.notifyLastTime = moment(subscriber.notifyLastTime).format(DATE_TIME_FORMAT);
    }
}
