import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SdpSharedModule } from 'app/shared';
import {
    SmsContentComponent,
    SmsContentDetailComponent,
    SmsContentUpdateComponent,
    SmsContentDeletePopupComponent,
    SmsContentDeleteDialogComponent,
    smsContentRoute,
    smsContentPopupRoute
} from './';

const ENTITY_STATES = [...smsContentRoute, ...smsContentPopupRoute];

@NgModule({
    imports: [SdpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SmsContentComponent,
        SmsContentDetailComponent,
        SmsContentUpdateComponent,
        SmsContentDeleteDialogComponent,
        SmsContentDeletePopupComponent
    ],
    entryComponents: [SmsContentComponent, SmsContentUpdateComponent, SmsContentDeleteDialogComponent, SmsContentDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SdpSmsContentModule {}
