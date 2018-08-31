import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SdpSharedModule } from 'app/shared';
import {
    SubProductComponent,
    SubProductDetailComponent,
    SubProductUpdateComponent,
    SubProductDeletePopupComponent,
    SubProductDeleteDialogComponent,
    subProductRoute,
    subProductPopupRoute
} from './';

const ENTITY_STATES = [...subProductRoute, ...subProductPopupRoute];

@NgModule({
    imports: [SdpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SubProductComponent,
        SubProductDetailComponent,
        SubProductUpdateComponent,
        SubProductDeleteDialogComponent,
        SubProductDeletePopupComponent
    ],
    entryComponents: [SubProductComponent, SubProductUpdateComponent, SubProductDeleteDialogComponent, SubProductDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SdpSubProductModule {}
