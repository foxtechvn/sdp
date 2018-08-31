import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISubscriber } from 'app/shared/model/subscriber.model';

type EntityResponseType = HttpResponse<ISubscriber>;
type EntityArrayResponseType = HttpResponse<ISubscriber[]>;

@Injectable({ providedIn: 'root' })
export class SubscriberService {
    private resourceUrl = SERVER_API_URL + 'api/subscribers';

    constructor(private http: HttpClient) {}

    create(subscriber: ISubscriber): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(subscriber);
        return this.http
            .post<ISubscriber>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(subscriber: ISubscriber): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(subscriber);
        return this.http
            .put<ISubscriber>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<ISubscriber>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISubscriber[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(subscriber: ISubscriber): ISubscriber {
        const copy: ISubscriber = Object.assign({}, subscriber, {
            joinAt: subscriber.joinAt != null && subscriber.joinAt.isValid() ? subscriber.joinAt.toJSON() : null,
            leftAt: subscriber.leftAt != null && subscriber.leftAt.isValid() ? subscriber.leftAt.toJSON() : null,
            expiryTime: subscriber.expiryTime != null && subscriber.expiryTime.isValid() ? subscriber.expiryTime.toJSON() : null,
            chargeLastTime:
                subscriber.chargeLastTime != null && subscriber.chargeLastTime.isValid() ? subscriber.chargeLastTime.toJSON() : null,
            chargeNextTime:
                subscriber.chargeNextTime != null && subscriber.chargeNextTime.isValid() ? subscriber.chargeNextTime.toJSON() : null,
            chargeLastSuccess:
                subscriber.chargeLastSuccess != null && subscriber.chargeLastSuccess.isValid()
                    ? subscriber.chargeLastSuccess.toJSON()
                    : null,
            notifyLastTime:
                subscriber.notifyLastTime != null && subscriber.notifyLastTime.isValid() ? subscriber.notifyLastTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.joinAt = res.body.joinAt != null ? moment(res.body.joinAt) : null;
        res.body.leftAt = res.body.leftAt != null ? moment(res.body.leftAt) : null;
        res.body.expiryTime = res.body.expiryTime != null ? moment(res.body.expiryTime) : null;
        res.body.chargeLastTime = res.body.chargeLastTime != null ? moment(res.body.chargeLastTime) : null;
        res.body.chargeNextTime = res.body.chargeNextTime != null ? moment(res.body.chargeNextTime) : null;
        res.body.chargeLastSuccess = res.body.chargeLastSuccess != null ? moment(res.body.chargeLastSuccess) : null;
        res.body.notifyLastTime = res.body.notifyLastTime != null ? moment(res.body.notifyLastTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((subscriber: ISubscriber) => {
            subscriber.joinAt = subscriber.joinAt != null ? moment(subscriber.joinAt) : null;
            subscriber.leftAt = subscriber.leftAt != null ? moment(subscriber.leftAt) : null;
            subscriber.expiryTime = subscriber.expiryTime != null ? moment(subscriber.expiryTime) : null;
            subscriber.chargeLastTime = subscriber.chargeLastTime != null ? moment(subscriber.chargeLastTime) : null;
            subscriber.chargeNextTime = subscriber.chargeNextTime != null ? moment(subscriber.chargeNextTime) : null;
            subscriber.chargeLastSuccess = subscriber.chargeLastSuccess != null ? moment(subscriber.chargeLastSuccess) : null;
            subscriber.notifyLastTime = subscriber.notifyLastTime != null ? moment(subscriber.notifyLastTime) : null;
        });
        return res;
    }
}
