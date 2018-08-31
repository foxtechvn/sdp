import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISmsContent } from 'app/shared/model/sms-content.model';
import { SmsContentService } from './sms-content.service';

@Component({
    selector: 'jhi-sms-content-delete-dialog',
    templateUrl: './sms-content-delete-dialog.component.html'
})
export class SmsContentDeleteDialogComponent {
    smsContent: ISmsContent;

    constructor(private smsContentService: SmsContentService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.smsContentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'smsContentListModification',
                content: 'Deleted an smsContent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sms-content-delete-popup',
    template: ''
})
export class SmsContentDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ smsContent }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SmsContentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.smsContent = smsContent;
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
