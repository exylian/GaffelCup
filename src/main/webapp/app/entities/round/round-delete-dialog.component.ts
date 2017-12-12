import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Round } from './round.model';
import { RoundPopupService } from './round-popup.service';
import { RoundService } from './round.service';

@Component({
    selector: 'jhi-round-delete-dialog',
    templateUrl: './round-delete-dialog.component.html'
})
export class RoundDeleteDialogComponent {

    round: Round;

    constructor(
        private roundService: RoundService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.roundService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'roundListModification',
                content: 'Deleted an round'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-round-delete-popup',
    template: ''
})
export class RoundDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private roundPopupService: RoundPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.roundPopupService
                .open(RoundDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
