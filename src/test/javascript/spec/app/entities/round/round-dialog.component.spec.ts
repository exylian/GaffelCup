/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { GaffelCupTestModule } from '../../../test.module';
import { RoundDialogComponent } from '../../../../../../main/webapp/app/entities/round/round-dialog.component';
import { RoundService } from '../../../../../../main/webapp/app/entities/round/round.service';
import { Round } from '../../../../../../main/webapp/app/entities/round/round.model';

describe('Component Tests', () => {

    describe('Round Management Dialog Component', () => {
        let comp: RoundDialogComponent;
        let fixture: ComponentFixture<RoundDialogComponent>;
        let service: RoundService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GaffelCupTestModule],
                declarations: [RoundDialogComponent],
                providers: [
                    RoundService
                ]
            })
            .overrideTemplate(RoundDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RoundDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoundService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Round(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.round = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'roundListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Round();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.round = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'roundListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
