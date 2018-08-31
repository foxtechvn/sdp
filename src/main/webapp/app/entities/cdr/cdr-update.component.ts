import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICdr } from 'app/shared/model/cdr.model';
import { CdrService } from './cdr.service';

@Component({
    selector: 'jhi-cdr-update',
    templateUrl: './cdr-update.component.html'
})
export class CdrUpdateComponent implements OnInit {
    private _cdr: ICdr;
    isSaving: boolean;
    requestAt: string;
    responseAt: string;

    constructor(private cdrService: CdrService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cdr }) => {
            this.cdr = cdr;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.cdr.requestAt = moment(this.requestAt, DATE_TIME_FORMAT);
        this.cdr.responseAt = moment(this.responseAt, DATE_TIME_FORMAT);
        if (this.cdr.id !== undefined) {
            this.subscribeToSaveResponse(this.cdrService.update(this.cdr));
        } else {
            this.subscribeToSaveResponse(this.cdrService.create(this.cdr));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICdr>>) {
        result.subscribe((res: HttpResponse<ICdr>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get cdr() {
        return this._cdr;
    }

    set cdr(cdr: ICdr) {
        this._cdr = cdr;
        this.requestAt = moment(cdr.requestAt).format(DATE_TIME_FORMAT);
        this.responseAt = moment(cdr.responseAt).format(DATE_TIME_FORMAT);
    }
}
