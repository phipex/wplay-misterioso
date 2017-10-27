import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WplayMisteriosoSharedModule } from '../../shared';
import {
    TicketGanadorService,
    TicketGanadorPopupService,
    TicketGanadorComponent,
    TicketGanadorDetailComponent,
    TicketGanadorDialogComponent,
    TicketGanadorPopupComponent,
    TicketGanadorDeletePopupComponent,
    TicketGanadorDeleteDialogComponent,
    ticketGanadorRoute,
    ticketGanadorPopupRoute,
    TicketGanadorResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ticketGanadorRoute,
    ...ticketGanadorPopupRoute,
];

@NgModule({
    imports: [
        WplayMisteriosoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TicketGanadorComponent,
        TicketGanadorDetailComponent,
        TicketGanadorDialogComponent,
        TicketGanadorDeleteDialogComponent,
        TicketGanadorPopupComponent,
        TicketGanadorDeletePopupComponent,
    ],
    entryComponents: [
        TicketGanadorComponent,
        TicketGanadorDialogComponent,
        TicketGanadorPopupComponent,
        TicketGanadorDeleteDialogComponent,
        TicketGanadorDeletePopupComponent,
    ],
    providers: [
        TicketGanadorService,
        TicketGanadorPopupService,
        TicketGanadorResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WplayMisteriosoTicketGanadorModule {}
