import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { GaffelCupSharedModule, UserRouteAccessService } from './shared';
import { GaffelCupAppRoutingModule} from './app-routing.module';
import { GaffelCupHomeModule } from './home/home.module';
import { GaffelCupAdminModule } from './admin/admin.module';
import { GaffelCupAccountModule } from './account/account.module';
import { GaffelCupEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        GaffelCupAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        GaffelCupSharedModule,
        GaffelCupHomeModule,
        GaffelCupAdminModule,
        GaffelCupAccountModule,
        GaffelCupEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class GaffelCupAppModule {}
