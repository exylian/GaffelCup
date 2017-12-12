/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { GaffelCupTestModule } from '../../../test.module';
import { ContestantDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/contestant/contestant-delete-dialog.component';
import { ContestantService } from '../../../../../../main/webapp/app/entities/contestant/contestant.service';

describe('Component Tests', () => {

    describe('Contestant Management Delete Component', () => {
        let comp: ContestantDeleteDialogComponent;
        let fixture: ComponentFixture<ContestantDeleteDialogComponent>;
        let service: ContestantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GaffelCupTestModule],
                declarations: [ContestantDeleteDialogComponent],
                providers: [
                    ContestantService
                ]
            })
            .overrideTemplate(ContestantDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContestantDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContestantService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
