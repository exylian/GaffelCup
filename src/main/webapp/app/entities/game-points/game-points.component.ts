import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GamePoints } from './game-points.model';
import { GamePointsService } from './game-points.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-game-points',
    templateUrl: './game-points.component.html'
})
export class GamePointsComponent implements OnInit, OnDestroy {
gamePoints: GamePoints[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private gamePointsService: GamePointsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.gamePointsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.gamePoints = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGamePoints();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: GamePoints) {
        return item.id;
    }
    registerChangeInGamePoints() {
        this.eventSubscriber = this.eventManager.subscribe('gamePointsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
