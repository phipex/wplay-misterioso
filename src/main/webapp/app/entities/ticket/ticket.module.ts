import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WplayMisteriosoSharedModule } from '../../shared';
import {
    TicketService,
    TicketPopupService,
    TicketComponent,
    TicketDetailComponent,
    TicketDialogComponent,
    TicketPopupComponent,
    TicketDeletePopupComponent,
    TicketDeleteDialogComponent,
    ticketRoute,
    ticketPopupRoute,
    TicketResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ticketRoute,
    ...ticketPopupRoute,
];

@NgModule({
    imports: [
        WplayMisteriosoSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TicketComponent,
        TicketDetailComponent,
        TicketDialogComponent,
        TicketDeleteDialogComponent,
        TicketPopupComponent,
        TicketDeletePopupComponent,
    ],
    entryComponents: [
        TicketComponent,
        TicketDialogComponent,
        TicketPopupComponent,
        TicketDeleteDialogComponent,
        TicketDeletePopupComponent,
    ],
    providers: [
        TicketService,
        TicketPopupService,
        TicketResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WplayMisteriosoTicketModule {}
