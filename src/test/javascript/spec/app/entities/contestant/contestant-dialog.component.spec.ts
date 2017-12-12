/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { GaffelCupTestModule } from '../../../test.module';
import { ContestantDialogComponent } from '../../../../../../main/webapp/app/entities/contestant/contestant-dialog.component';
import { ContestantService } from '../../../../../../main/webapp/app/entities/contestant/contestant.service';
import { Contestant } from '../../../../../../main/webapp/app/entities/contestant/contestant.model';

describe('Component Tests', () => {

    describe('Contestant Management Dialog Component', () => {
        let comp: ContestantDialogComponent;
        let fixture: ComponentFixture<ContestantDialogComponent>;
        let service: ContestantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GaffelCupTestModule],
                declarations: [ContestantDialogComponent],
                providers: [
                    ContestantService
                ]
            })
            .overrideTemplate(ContestantDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContestantDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContestantService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Contestant(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.contestant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'contestantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Contestant();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.contestant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'contestantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
