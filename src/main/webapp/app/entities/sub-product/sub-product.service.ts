import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISubProduct } from 'app/shared/model/sub-product.model';

type EntityResponseType = HttpResponse<ISubProduct>;
type EntityArrayResponseType = HttpResponse<ISubProduct[]>;

@Injectable({ providedIn: 'root' })
export class SubProductService {
    private resourceUrl = SERVER_API_URL + 'api/sub-products';

    constructor(private http: HttpClient) {}

    create(subProduct: ISubProduct): Observable<EntityResponseType> {
        return this.http.post<ISubProduct>(this.resourceUrl, subProduct, { observe: 'response' });
    }

    update(subProduct: ISubProduct): Observable<EntityResponseType> {
        return this.http.put<ISubProduct>(this.resourceUrl, subProduct, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ISubProduct>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISubProduct[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
