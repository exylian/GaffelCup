import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ContestantComponent } from './contestant.component';
import { ContestantDetailComponent } from './contestant-detail.component';
import { ContestantPopupComponent } from './contestant-dialog.component';
import { ContestantDeletePopupComponent } from './contestant-delete-dialog.component';
import {ContestantRegisterPopupComponent} from './register/contestant-register-dialog.component';

export const contestantRoute: Routes = [
    {
        path: 'contestant',
        component: ContestantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contestants'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'contestant/:id',
        component: ContestantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contestants'
        },
        canActivate: [UserRouteAccessService]
    },
];

export const contestantPopupRoute: Routes = [
    {
        path: 'contestant-new',
        component: ContestantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contestants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contestant-register',
        component: ContestantRegisterPopupComponent,
        data: {
            authorities: ['ROLE_ANONYMOUS'],
            pageTitle: 'Anmeldung'
        },
        outlet: 'popup'
    },
    {
        path: 'contestant/:id/edit',
        component: ContestantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contestants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contestant/:id/delete',
        component: ContestantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Contestants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
