import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDnd } from 'app/shared/model/dnd.model';
import { DndService } from './dnd.service';

@Component({
    selector: 'jhi-dnd-update',
    templateUrl: './dnd-update.component.html'
})
export class DndUpdateComponent implements OnInit {
    private _dnd: IDnd;
    isSaving: boolean;
    joinAt: string;

    constructor(private dndService: DndService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dnd }) => {
            this.dnd = dnd;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.dnd.joinAt = moment(this.joinAt, DATE_TIME_FORMAT);
        if (this.dnd.id !== undefined) {
            this.subscribeToSaveResponse(this.dndService.update(this.dnd));
        } else {
            this.subscribeToSaveResponse(this.dndService.create(this.dnd));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDnd>>) {
        result.subscribe((res: HttpResponse<IDnd>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get dnd() {
        return this._dnd;
    }

    set dnd(dnd: IDnd) {
        this._dnd = dnd;
        this.joinAt = moment(dnd.joinAt).format(DATE_TIME_FORMAT);
    }
}
