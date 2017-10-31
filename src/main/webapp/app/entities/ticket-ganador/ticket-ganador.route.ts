import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TicketGanadorComponent } from './ticket-ganador.component';
import { TicketGanadorDetailComponent } from './ticket-ganador-detail.component';
import { TicketGanadorPopupComponent } from './ticket-ganador-dialog.component';
import { TicketGanadorDeletePopupComponent } from './ticket-ganador-delete-dialog.component';

@Injectable()
export class TicketGanadorResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const ticketGanadorRoute: Routes = [
    {
        path: 'ticket-ganador',
        component: TicketGanadorComponent,
        resolve: {
            'pagingParams': TicketGanadorResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.ticketGanador.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ticket-ganador/:id',
        component: TicketGanadorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.ticketGanador.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ticketGanadorPopupRoute: Routes = [
    {
        path: 'ticket-ganador-new',
        component: TicketGanadorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.ticketGanador.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ticket-ganador/:id/edit',
        component: TicketGanadorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.ticketGanador.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ticket-ganador/:id/delete',
        component: TicketGanadorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.ticketGanador.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
