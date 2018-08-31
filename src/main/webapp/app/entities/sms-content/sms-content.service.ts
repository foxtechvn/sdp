import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISmsContent } from 'app/shared/model/sms-content.model';

type EntityResponseType = HttpResponse<ISmsContent>;
type EntityArrayResponseType = HttpResponse<ISmsContent[]>;

@Injectable({ providedIn: 'root' })
export class SmsContentService {
    private resourceUrl = SERVER_API_URL + 'api/sms-contents';

    constructor(private http: HttpClient) {}

    create(smsContent: ISmsContent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(smsContent);
        return this.http
            .post<ISmsContent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(smsContent: ISmsContent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(smsContent);
        return this.http
            .put<ISmsContent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<ISmsContent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISmsContent[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(smsContent: ISmsContent): ISmsContent {
        const copy: ISmsContent = Object.assign({}, smsContent, {
            startAt: smsContent.startAt != null && smsContent.startAt.isValid() ? smsContent.startAt.toJSON() : null,
            expiredAt: smsContent.expiredAt != null && smsContent.expiredAt.isValid() ? smsContent.expiredAt.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.startAt = res.body.startAt != null ? moment(res.body.startAt) : null;
        res.body.expiredAt = res.body.expiredAt != null ? moment(res.body.expiredAt) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((smsContent: ISmsContent) => {
            smsContent.startAt = smsContent.startAt != null ? moment(smsContent.startAt) : null;
            smsContent.expiredAt = smsContent.expiredAt != null ? moment(smsContent.expiredAt) : null;
        });
        return res;
    }
}
