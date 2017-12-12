import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { GamePointsComponent } from './game-points.component';
import { GamePointsDetailComponent } from './game-points-detail.component';
import { GamePointsPopupComponent } from './game-points-dialog.component';
import { GamePointsDeletePopupComponent } from './game-points-delete-dialog.component';

export const gamePointsRoute: Routes = [
    {
        path: 'game-points',
        component: GamePointsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GamePoints'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'game-points/:id',
        component: GamePointsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GamePoints'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gamePointsPopupRoute: Routes = [
    {
        path: 'game-points-new',
        component: GamePointsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GamePoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'game-points/:id/edit',
        component: GamePointsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GamePoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'game-points/:id/delete',
        component: GamePointsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GamePoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
