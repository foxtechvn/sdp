import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICdr } from 'app/shared/model/cdr.model';

type EntityResponseType = HttpResponse<ICdr>;
type EntityArrayResponseType = HttpResponse<ICdr[]>;

@Injectable({ providedIn: 'root' })
export class CdrService {
    private resourceUrl = SERVER_API_URL + 'api/cdrs';

    constructor(private http: HttpClient) {}

    create(cdr: ICdr): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cdr);
        return this.http
            .post<ICdr>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(cdr: ICdr): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cdr);
        return this.http
            .put<ICdr>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<ICdr>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICdr[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(cdr: ICdr): ICdr {
        const copy: ICdr = Object.assign({}, cdr, {
            requestAt: cdr.requestAt != null && cdr.requestAt.isValid() ? cdr.requestAt.toJSON() : null,
            responseAt: cdr.responseAt != null && cdr.responseAt.isValid() ? cdr.responseAt.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.requestAt = res.body.requestAt != null ? moment(res.body.requestAt) : null;
        res.body.responseAt = res.body.responseAt != null ? moment(res.body.responseAt) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((cdr: ICdr) => {
            cdr.requestAt = cdr.requestAt != null ? moment(cdr.requestAt) : null;
            cdr.responseAt = cdr.responseAt != null ? moment(cdr.responseAt) : null;
        });
        return res;
    }
}
