import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WplayMisteriosoSharedModule } from '../../shared';
import {
    MisteriosoService,
    MisteriosoPopupService,
    MisteriosoComponent,
    MisteriosoDetailComponent,
    MisteriosoDialogComponent,
    MisteriosoPopupComponent,
    MisteriosoDeletePopupComponent,
    MisteriosoDeleteDialogComponent,
    misteriosoRoute,
    misteriosoPopupRoute,
    MisteriosoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...misteriosoRoute,
    ...misteriosoPopupRoute,
];

@NgModule({
    imports: [
        WplayMisteriosoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MisteriosoComponent,
        MisteriosoDetailComponent,
        MisteriosoDialogComponent,
        MisteriosoDeleteDialogComponent,
        MisteriosoPopupComponent,
        MisteriosoDeletePopupComponent,
    ],
    entryComponents: [
        MisteriosoComponent,
        MisteriosoDialogComponent,
        MisteriosoPopupComponent,
        MisteriosoDeleteDialogComponent,
        MisteriosoDeletePopupComponent,
    ],
    providers: [
        MisteriosoService,
        MisteriosoPopupService,
        MisteriosoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WplayMisteriosoMisteriosoModule {}
