import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDnd } from 'app/shared/model/dnd.model';

type EntityResponseType = HttpResponse<IDnd>;
type EntityArrayResponseType = HttpResponse<IDnd[]>;

@Injectable({ providedIn: 'root' })
export class DndService {
    private resourceUrl = SERVER_API_URL + 'api/dnds';

    constructor(private http: HttpClient) {}

    create(dnd: IDnd): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dnd);
        return this.http
            .post<IDnd>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(dnd: IDnd): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dnd);
        return this.http
            .put<IDnd>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<IDnd>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDnd[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(dnd: IDnd): IDnd {
        const copy: IDnd = Object.assign({}, dnd, {
            joinAt: dnd.joinAt != null && dnd.joinAt.isValid() ? dnd.joinAt.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.joinAt = res.body.joinAt != null ? moment(res.body.joinAt) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((dnd: IDnd) => {
            dnd.joinAt = dnd.joinAt != null ? moment(dnd.joinAt) : null;
        });
        return res;
    }
}
