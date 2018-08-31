import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SmsContent } from 'app/shared/model/sms-content.model';
import { SmsContentService } from './sms-content.service';
import { SmsContentComponent } from './sms-content.component';
import { SmsContentDetailComponent } from './sms-content-detail.component';
import { SmsContentUpdateComponent } from './sms-content-update.component';
import { SmsContentDeletePopupComponent } from './sms-content-delete-dialog.component';
import { ISmsContent } from 'app/shared/model/sms-content.model';

@Injectable({ providedIn: 'root' })
export class SmsContentResolve implements Resolve<ISmsContent> {
    constructor(private service: SmsContentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((smsContent: HttpResponse<SmsContent>) => smsContent.body));
        }
        return of(new SmsContent());
    }
}

export const smsContentRoute: Routes = [
    {
        path: 'sms-content',
        component: SmsContentComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sdpApp.smsContent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sms-content/:id/view',
        component: SmsContentDetailComponent,
        resolve: {
            smsContent: SmsContentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.smsContent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sms-content/new',
        component: SmsContentUpdateComponent,
        resolve: {
            smsContent: SmsContentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.smsContent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sms-content/:id/edit',
        component: SmsContentUpdateComponent,
        resolve: {
            smsContent: SmsContentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.smsContent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const smsContentPopupRoute: Routes = [
    {
        path: 'sms-content/:id/delete',
        component: SmsContentDeletePopupComponent,
        resolve: {
            smsContent: SmsContentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.smsContent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
