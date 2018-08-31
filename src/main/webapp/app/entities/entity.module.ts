import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SdpDndModule } from './dnd/dnd.module';
import { SdpSubProductModule } from './sub-product/sub-product.module';
import { SdpSmsContentModule } from './sms-content/sms-content.module';
import { SdpSmsModule } from './sms/sms.module';
import { SdpCdrModule } from './cdr/cdr.module';
import { SdpSubscriberModule } from './subscriber/subscriber.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SdpDndModule,
        SdpSubProductModule,
        SdpSmsContentModule,
        SdpSmsModule,
        SdpCdrModule,
        SdpSubscriberModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SdpEntityModule {}
