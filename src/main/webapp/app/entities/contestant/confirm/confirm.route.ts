import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../../shared';
import { ConfirmComponent } from './confirm.component';

export const activateRoute: Route = {
    path: 'confirm',
    component: ConfirmComponent,
    data: {
        authorities: [],
        pageTitle: 'Activation'
    },
    canActivate: [UserRouteAccessService]
};
