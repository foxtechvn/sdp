import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SubProduct } from 'app/shared/model/sub-product.model';
import { SubProductService } from './sub-product.service';
import { SubProductComponent } from './sub-product.component';
import { SubProductDetailComponent } from './sub-product-detail.component';
import { SubProductUpdateComponent } from './sub-product-update.component';
import { SubProductDeletePopupComponent } from './sub-product-delete-dialog.component';
import { ISubProduct } from 'app/shared/model/sub-product.model';

@Injectable({ providedIn: 'root' })
export class SubProductResolve implements Resolve<ISubProduct> {
    constructor(private service: SubProductService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((subProduct: HttpResponse<SubProduct>) => subProduct.body));
        }
        return of(new SubProduct());
    }
}

export const subProductRoute: Routes = [
    {
        path: 'sub-product',
        component: SubProductComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sdpApp.subProduct.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sub-product/:id/view',
        component: SubProductDetailComponent,
        resolve: {
            subProduct: SubProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.subProduct.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sub-product/new',
        component: SubProductUpdateComponent,
        resolve: {
            subProduct: SubProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.subProduct.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sub-product/:id/edit',
        component: SubProductUpdateComponent,
        resolve: {
            subProduct: SubProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.subProduct.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const subProductPopupRoute: Routes = [
    {
        path: 'sub-product/:id/delete',
        component: SubProductDeletePopupComponent,
        resolve: {
            subProduct: SubProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sdpApp.subProduct.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
