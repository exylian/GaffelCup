import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';

import { ConfirmService } from './confirm.service';

@Component({
    selector: 'jhi-activate',
    templateUrl: './confirm.component.html'
})
export class ConfirmComponent implements OnInit {
    error: string;
    success: string;
    modalRef: NgbModalRef;

    constructor(
        private confirmService: ConfirmService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.route.queryParams.subscribe((params) => {
            this.confirmService.get(params['key']).subscribe(() => {
                this.error = null;
                this.success = 'OK';
            }, () => {
                this.success = null;
                this.error = 'ERROR';
            });
        });
    }
}
