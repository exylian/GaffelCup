import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RoundComponent } from './round.component';
import { RoundDetailComponent } from './round-detail.component';
import { RoundPopupComponent } from './round-dialog.component';
import { RoundDeletePopupComponent } from './round-delete-dialog.component';

export const roundRoute: Routes = [
    {
        path: 'round',
        component: RoundComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rounds'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'round/:id',
        component: RoundDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rounds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const roundPopupRoute: Routes = [
    {
        path: 'round-new',
        component: RoundPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rounds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'round/:id/edit',
        component: RoundPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rounds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'round/:id/delete',
        component: RoundDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rounds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
