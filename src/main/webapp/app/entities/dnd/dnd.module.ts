import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SdpSharedModule } from 'app/shared';
import {
    DndComponent,
    DndDetailComponent,
    DndUpdateComponent,
    DndDeletePopupComponent,
    DndDeleteDialogComponent,
    dndRoute,
    dndPopupRoute
} from './';

const ENTITY_STATES = [...dndRoute, ...dndPopupRoute];

@NgModule({
    imports: [SdpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DndComponent, DndDetailComponent, DndUpdateComponent, DndDeleteDialogComponent, DndDeletePopupComponent],
    entryComponents: [DndComponent, DndUpdateComponent, DndDeleteDialogComponent, DndDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SdpDndModule {}
