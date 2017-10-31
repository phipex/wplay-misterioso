import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MisteriosoComponent } from './misterioso.component';
import { MisteriosoDetailComponent } from './misterioso-detail.component';
import { MisteriosoPopupComponent } from './misterioso-dialog.component';
import { MisteriosoDeletePopupComponent } from './misterioso-delete-dialog.component';

@Injectable()
export class MisteriosoResolvePagingParams implements Resolve<any> {

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

export const misteriosoRoute: Routes = [
    {
        path: 'misterioso',
        component: MisteriosoComponent,
        resolve: {
            'pagingParams': MisteriosoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.misterioso.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'misterioso/:id',
        component: MisteriosoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.misterioso.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const misteriosoPopupRoute: Routes = [
    {
        path: 'misterioso-new',
        component: MisteriosoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.misterioso.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'misterioso/:id/edit',
        component: MisteriosoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.misterioso.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'misterioso/:id/delete',
        component: MisteriosoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'wplayMisteriosoApp.misterioso.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
