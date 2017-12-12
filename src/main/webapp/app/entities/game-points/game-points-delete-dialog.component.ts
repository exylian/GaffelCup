import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GamePoints } from './game-points.model';
import { GamePointsPopupService } from './game-points-popup.service';
import { GamePointsService } from './game-points.service';

@Component({
    selector: 'jhi-game-points-delete-dialog',
    templateUrl: './game-points-delete-dialog.component.html'
})
export class GamePointsDeleteDialogComponent {

    gamePoints: GamePoints;

    constructor(
        private gamePointsService: GamePointsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gamePointsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'gamePointsListModification',
                content: 'Deleted an gamePoints'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-game-points-delete-popup',
    template: ''
})
export class GamePointsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gamePointsPopupService: GamePointsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.gamePointsPopupService
                .open(GamePointsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
