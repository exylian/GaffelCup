import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../../shared';
import { UnconfirmComponent } from './unconfirm.component';

export const unconfirmRoute: Route = {
    path: 'unconfirm',
    component: UnconfirmComponent,
    data: {
        authorities: [],
        pageTitle: 'Abmeldung'
    },
    canActivate: [UserRouteAccessService]
};
