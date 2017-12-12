import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Contestant } from './contestant.model';
import { ContestantService } from './contestant.service';

@Injectable()
export class ContestantPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private contestantService: ContestantService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.contestantService.find(id).subscribe((contestant) => {
                    contestant.confirmedAt = this.datePipe
                        .transform(contestant.confirmedAt, 'yyyy-MM-ddTHH:mm:ss');
                    contestant.payedAt = this.datePipe
                        .transform(contestant.payedAt, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.contestantModalRef(component, contestant);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.contestantModalRef(component, new Contestant());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    contestantModalRef(component: Component, contestant: Contestant): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.contestant = contestant;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
