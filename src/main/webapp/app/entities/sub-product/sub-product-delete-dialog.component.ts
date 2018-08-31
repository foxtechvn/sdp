import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubProduct } from 'app/shared/model/sub-product.model';
import { SubProductService } from './sub-product.service';

@Component({
    selector: 'jhi-sub-product-delete-dialog',
    templateUrl: './sub-product-delete-dialog.component.html'
})
export class SubProductDeleteDialogComponent {
    subProduct: ISubProduct;

    constructor(private subProductService: SubProductService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.subProductService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'subProductListModification',
                content: 'Deleted an subProduct'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sub-product-delete-popup',
    template: ''
})
export class SubProductDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ subProduct }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SubProductDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.subProduct = subProduct;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
