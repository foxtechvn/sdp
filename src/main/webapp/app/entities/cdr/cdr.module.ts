import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SdpSharedModule } from 'app/shared';
import {
    CdrComponent,
    CdrDetailComponent,
    CdrUpdateComponent,
    CdrDeletePopupComponent,
    CdrDeleteDialogComponent,
    cdrRoute,
    cdrPopupRoute
} from './';

const ENTITY_STATES = [...cdrRoute, ...cdrPopupRoute];

@NgModule({
    imports: [SdpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CdrComponent, CdrDetailComponent, CdrUpdateComponent, CdrDeleteDialogComponent, CdrDeletePopupComponent],
    entryComponents: [CdrComponent, CdrUpdateComponent, CdrDeleteDialogComponent, CdrDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SdpCdrModule {}
