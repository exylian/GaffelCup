import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Contestant } from './contestant.model';
import { ContestantService } from './contestant.service';

@Component({
    selector: 'jhi-contestant-detail',
    templateUrl: './contestant-detail.component.html'
})
export class ContestantDetailComponent implements OnInit, OnDestroy {

    contestant: Contestant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private contestantService: ContestantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContestants();
    }

    load(id) {
        this.contestantService.find(id).subscribe((contestant) => {
            this.contestant = contestant;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInContestants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'contestantListModification',
            (response) => this.load(this.contestant.id)
        );
    }
}
