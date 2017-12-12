import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GaffelCupSharedModule } from '../../shared';
import {
    GamePointsService,
    GamePointsPopupService,
    GamePointsComponent,
    GamePointsDetailComponent,
    GamePointsDialogComponent,
    GamePointsPopupComponent,
    GamePointsDeletePopupComponent,
    GamePointsDeleteDialogComponent,
    gamePointsRoute,
    gamePointsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...gamePointsRoute,
    ...gamePointsPopupRoute,
];

@NgModule({
    imports: [
        GaffelCupSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GamePointsComponent,
        GamePointsDetailComponent,
        GamePointsDialogComponent,
        GamePointsDeleteDialogComponent,
        GamePointsPopupComponent,
        GamePointsDeletePopupComponent,
    ],
    entryComponents: [
        GamePointsComponent,
        GamePointsDialogComponent,
        GamePointsPopupComponent,
        GamePointsDeleteDialogComponent,
        GamePointsDeletePopupComponent,
    ],
    providers: [
        GamePointsService,
        GamePointsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GaffelCupGamePointsModule {}
