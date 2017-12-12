import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Contestant } from './contestant.model';
import { ContestantService } from './contestant.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-contestant',
    templateUrl: './contestant.component.html'
})
export class ContestantComponent implements OnInit, OnDestroy {
contestants: Contestant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private contestantService: ContestantService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.contestantService.query().subscribe(
            (res: ResponseWrapper) => {
                this.contestants = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInContestants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Contestant) {
        return item.id;
    }
    registerChangeInContestants() {
        this.eventSubscriber = this.eventManager.subscribe('contestantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
