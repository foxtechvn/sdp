import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Dnd } from 'app/shared/model/dnd.model';
import { DndService } from './dnd.service';
import { DndComponent } from './dnd.component';
import { DndDetailComponent } from './dnd-detail.component';
import { DndUpdateComponent } from './dnd-update.component';
import { DndDeletePopupComponent } from './dnd-delete-dialog.component';
import { IDnd } from 'app/shared/model/dnd.model';

@Injectable({ providedIn: 'root' })
export class DndResolve implements Resolve<IDnd> {
    constructor(private service: DndService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((dnd: HttpResponse<Dnd>) => dnd.body));
        }
        return of(new Dnd());
    }
}

export const dndRoute: Routes = [
    {
        path: 'dnd',
        component: DndComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sdpApp.dnd.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dnd/:id/view',
        component: DndDetailComponent,
        resolve: {
            dnd: DndResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.dnd.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dnd/new',
        component: DndUpdateComponent,
        resolve: {
            dnd: DndResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.dnd.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dnd/:id/edit',
        component: DndUpdateComponent,
        resolve: {
            dnd: DndResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.dnd.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dndPopupRoute: Routes = [
    {
        path: 'dnd/:id/delete',
        component: DndDeletePopupComponent,
        resolve: {
            dnd: DndResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.dnd.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
