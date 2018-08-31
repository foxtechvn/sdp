import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ISubProduct } from 'app/shared/model/sub-product.model';
import { SubProductService } from './sub-product.service';

@Component({
    selector: 'jhi-sub-product-update',
    templateUrl: './sub-product-update.component.html'
})
export class SubProductUpdateComponent implements OnInit {
    private _subProduct: ISubProduct;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private subProductService: SubProductService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ subProduct }) => {
            this.subProduct = subProduct;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.subProduct.id !== undefined) {
            this.subscribeToSaveResponse(this.subProductService.update(this.subProduct));
        } else {
            this.subscribeToSaveResponse(this.subProductService.create(this.subProduct));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISubProduct>>) {
        result.subscribe((res: HttpResponse<ISubProduct>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get subProduct() {
        return this._subProduct;
    }

    set subProduct(subProduct: ISubProduct) {
        this._subProduct = subProduct;
    }
}
