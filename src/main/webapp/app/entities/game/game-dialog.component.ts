import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Game } from './game.model';
import { GamePopupService } from './game-popup.service';
import { GameService } from './game.service';
import { Contestant, ContestantService } from '../contestant';
import { GamePoints, GamePointsService } from '../game-points';
import { Round, RoundService } from '../round';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-game-dialog',
    templateUrl: './game-dialog.component.html'
})
export class GameDialogComponent implements OnInit {

    game: Game;
    isSaving: boolean;

    contestants: Contestant[];

    gamepoints: GamePoints[];

    rounds: Round[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private gameService: GameService,
        private contestantService: ContestantService,
        private gamePointsService: GamePointsService,
        private roundService: RoundService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.contestantService.query()
            .subscribe((res: ResponseWrapper) => { this.contestants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.gamePointsService.query()
            .subscribe((res: ResponseWrapper) => { this.gamepoints = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.roundService.query()
            .subscribe((res: ResponseWrapper) => { this.rounds = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.game.id !== undefined) {
            this.subscribeToSaveResponse(
                this.gameService.update(this.game));
        } else {
            this.subscribeToSaveResponse(
                this.gameService.create(this.game));
        }
    }

    private subscribeToSaveResponse(result: Observable<Game>) {
        result.subscribe((res: Game) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Game) {
        this.eventManager.broadcast({ name: 'gameListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackContestantById(index: number, item: Contestant) {
        return item.id;
    }

    trackGamePointsById(index: number, item: GamePoints) {
        return item.id;
    }

    trackRoundById(index: number, item: Round) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-game-popup',
    template: ''
})
export class GamePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gamePopupService: GamePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.gamePopupService
                    .open(GameDialogComponent as Component, params['id']);
            } else {
                this.gamePopupService
                    .open(GameDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
