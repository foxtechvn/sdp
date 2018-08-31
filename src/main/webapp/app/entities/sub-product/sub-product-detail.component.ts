import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISubProduct } from 'app/shared/model/sub-product.model';

@Component({
    selector: 'jhi-sub-product-detail',
    templateUrl: './sub-product-detail.component.html'
})
export class SubProductDetailComponent implements OnInit {
    subProduct: ISubProduct;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
