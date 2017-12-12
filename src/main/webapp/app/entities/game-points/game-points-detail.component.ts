import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { GamePoints } from './game-points.model';
import { GamePointsService } from './game-points.service';

@Component({
    selector: 'jhi-game-points-detail',
    templateUrl: './game-points-detail.component.html'
})
export class GamePointsDetailComponent implements OnInit, OnDestroy {

    gamePoints: GamePoints;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private gamePointsService: GamePointsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGamePoints();
    }

    load(id) {
        this.gamePointsService.find(id).subscribe((gamePoints) => {
            this.gamePoints = gamePoints;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGamePoints() {
        this.eventSubscriber = this.eventManager.subscribe(
            'gamePointsListModification',
            (response) => this.load(this.gamePoints.id)
        );
    }
}
