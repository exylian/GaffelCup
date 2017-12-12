import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GamePoints } from './game-points.model';
import { GamePointsPopupService } from './game-points-popup.service';
import { GamePointsService } from './game-points.service';

@Component({
    selector: 'jhi-game-points-dialog',
    templateUrl: './game-points-dialog.component.html'
})
export class GamePointsDialogComponent implements OnInit {

    gamePoints: GamePoints;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private gamePointsService: GamePointsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.gamePoints.id !== undefined) {
            this.subscribeToSaveResponse(
                this.gamePointsService.update(this.gamePoints));
        } else {
            this.subscribeToSaveResponse(
                this.gamePointsService.create(this.gamePoints));
        }
    }

    private subscribeToSaveResponse(result: Observable<GamePoints>) {
        result.subscribe((res: GamePoints) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: GamePoints) {
        this.eventManager.broadcast({ name: 'gamePointsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-game-points-popup',
    template: ''
})
export class GamePointsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gamePointsPopupService: GamePointsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.gamePointsPopupService
                    .open(GamePointsDialogComponent as Component, params['id']);
            } else {
                this.gamePointsPopupService
                    .open(GamePointsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
