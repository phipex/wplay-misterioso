import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TicketComponent } from './ticket.component';
import { TicketDetailComponent } from './ticket-detail.component';
import { TicketPopupComponent } from './ticket-dialog.component';
import { TicketDeletePopupComponent } from './ticket-delete-dialog.component';

@Injectable()
export class TicketResolvePagingParams implements Resolve<any> {

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

export const ticketRoute: Routes = [
    {
        path: 'ticket',
        component: TicketComponent,
        resolve: {
            'pagingParams': TicketResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.ticket.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ticket/:id',
        component: TicketDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.ticket.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ticketPopupRoute: Routes = [
    {
        path: 'ticket-new',
        component: TicketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.ticket.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ticket/:id/edit',
        component: TicketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.ticket.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ticket/:id/delete',
        component: TicketDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.ticket.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
