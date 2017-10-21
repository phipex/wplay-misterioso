import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WplayMisteriosoMisteriosoModule } from './misterioso/misterioso.module';
import { WplayMisteriosoTicketModule } from './ticket/ticket.module';
import { WplayMisteriosoTicketGanadorModule } from './ticket-ganador/ticket-ganador.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        WplayMisteriosoMisteriosoModule,
        WplayMisteriosoTicketModule,
        WplayMisteriosoTicketGanadorModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WplayMisteriosoEntityModule {}
