import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GaffelCupContestantModule } from './contestant/contestant.module';
import { GaffelCupRoundModule } from './round/round.module';
import { GaffelCupGameModule } from './game/game.module';
import { GaffelCupGamePointsModule } from './game-points/game-points.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GaffelCupContestantModule,
        GaffelCupRoundModule,
        GaffelCupGameModule,
        GaffelCupGamePointsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GaffelCupEntityModule {}
