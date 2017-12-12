import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GaffelCupSharedModule } from '../../shared';
import {
    RoundService,
    RoundPopupService,
    RoundComponent,
    RoundDetailComponent,
    RoundDialogComponent,
    RoundPopupComponent,
    RoundDeletePopupComponent,
    RoundDeleteDialogComponent,
    roundRoute,
    roundPopupRoute,
} from './';

const ENTITY_STATES = [
    ...roundRoute,
    ...roundPopupRoute,
];

@NgModule({
    imports: [
        GaffelCupSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RoundComponent,
        RoundDetailComponent,
        RoundDialogComponent,
        RoundDeleteDialogComponent,
        RoundPopupComponent,
        RoundDeletePopupComponent,
    ],
    entryComponents: [
        RoundComponent,
        RoundDialogComponent,
        RoundPopupComponent,
        RoundDeleteDialogComponent,
        RoundDeletePopupComponent,
    ],
    providers: [
        RoundService,
        RoundPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GaffelCupRoundModule {}
