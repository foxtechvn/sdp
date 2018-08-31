import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Cdr } from 'app/shared/model/cdr.model';
import { CdrService } from './cdr.service';
import { CdrComponent } from './cdr.component';
import { CdrDetailComponent } from './cdr-detail.component';
import { CdrUpdateComponent } from './cdr-update.component';
import { CdrDeletePopupComponent } from './cdr-delete-dialog.component';
import { ICdr } from 'app/shared/model/cdr.model';

@Injectable({ providedIn: 'root' })
export class CdrResolve implements Resolve<ICdr> {
    constructor(private service: CdrService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((cdr: HttpResponse<Cdr>) => cdr.body));
        }
        return of(new Cdr());
    }
}

export const cdrRoute: Routes = [
    {
        path: 'cdr',
        component: CdrComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sdpApp.cdr.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cdr/:id/view',
        component: CdrDetailComponent,
        resolve: {
            cdr: CdrResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.cdr.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cdr/new',
        component: CdrUpdateComponent,
        resolve: {
            cdr: CdrResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.cdr.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cdr/:id/edit',
        component: CdrUpdateComponent,
        resolve: {
            cdr: CdrResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.cdr.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cdrPopupRoute: Routes = [
    {
        path: 'cdr/:id/delete',
        component: CdrDeletePopupComponent,
        resolve: {
            cdr: CdrResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.cdr.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
