import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Round } from './round.model';
import { RoundPopupService } from './round-popup.service';
import { RoundService } from './round.service';

@Component({
    selector: 'jhi-round-dialog',
    templateUrl: './round-dialog.component.html'
})
export class RoundDialogComponent implements OnInit {

    round: Round;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private roundService: RoundService,
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
        if (this.round.id !== undefined) {
            this.subscribeToSaveResponse(
                this.roundService.update(this.round));
        } else {
            this.subscribeToSaveResponse(
                this.roundService.create(this.round));
        }
    }

    private subscribeToSaveResponse(result: Observable<Round>) {
        result.subscribe((res: Round) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Round) {
        this.eventManager.broadcast({ name: 'roundListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-round-popup',
    template: ''
})
export class RoundPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private roundPopupService: RoundPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.roundPopupService
                    .open(RoundDialogComponent as Component, params['id']);
            } else {
                this.roundPopupService
                    .open(RoundDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
