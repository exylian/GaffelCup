import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Contestant } from './contestant.model';
import { ContestantPopupService } from './contestant-popup.service';
import { ContestantService } from './contestant.service';

@Component({
    selector: 'jhi-contestant-delete-dialog',
    templateUrl: './contestant-delete-dialog.component.html'
})
export class ContestantDeleteDialogComponent {

    contestant: Contestant;

    constructor(
        private contestantService: ContestantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contestantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contestantListModification',
                content: 'Deleted an contestant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contestant-delete-popup',
    template: ''
})
export class ContestantDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contestantPopupService: ContestantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.contestantPopupService
                .open(ContestantDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
