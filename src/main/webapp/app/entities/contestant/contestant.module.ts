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
    ConfirmComponent,
    ConfirmService,
    UnconfirmService,
    UnconfirmComponent
} from './';

import {ContestantRegisterDialogComponent, ContestantRegisterPopupComponent} from './register/contestant-register-dialog.component';

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
        ContestantRegisterDialogComponent,
        ContestantRegisterPopupComponent,
        ConfirmComponent,
        UnconfirmComponent
    ],
    entryComponents: [
        ContestantComponent,
        ContestantDialogComponent,
        ContestantPopupComponent,
        ContestantDeleteDialogComponent,
        ContestantDeletePopupComponent,
        ContestantRegisterDialogComponent,
        ContestantRegisterPopupComponent,
        ConfirmComponent,
        UnconfirmComponent
    ],
    providers: [
        ContestantService,
        ContestantPopupService,
        ConfirmService,
        UnconfirmService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GaffelCupContestantModule {}
