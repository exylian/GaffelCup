import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GaffelCupSharedModule } from '../../shared';
import {
    ContestantService,
    ContestantPopupService,
    ContestantComponent,
    ContestantDetailComponent,
    ContestantDialogComponent,
    ContestantPopupComponent,
    ContestantDeletePopupComponent,
    ContestantDeleteDialogComponent,
    contestantRoute,
    contestantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...contestantRoute,
    ...contestantPopupRoute,
];

@NgModule({
    imports: [
        GaffelCupSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ContestantComponent,
        ContestantDetailComponent,
        ContestantDialogComponent,
        ContestantDeleteDialogComponent,
        ContestantPopupComponent,
        ContestantDeletePopupComponent,
    ],
    entryComponents: [
        ContestantComponent,
        ContestantDialogComponent,
        ContestantPopupComponent,
        ContestantDeleteDialogComponent,
        ContestantDeletePopupComponent,
    ],
    providers: [
        ContestantService,
        ContestantPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GaffelCupContestantModule {}
