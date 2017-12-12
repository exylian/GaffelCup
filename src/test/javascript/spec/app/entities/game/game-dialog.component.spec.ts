/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { GaffelCupTestModule } from '../../../test.module';
import { GameDialogComponent } from '../../../../../../main/webapp/app/entities/game/game-dialog.component';
import { GameService } from '../../../../../../main/webapp/app/entities/game/game.service';
import { Game } from '../../../../../../main/webapp/app/entities/game/game.model';
import { ContestantService } from '../../../../../../main/webapp/app/entities/contestant';
import { GamePointsService } from '../../../../../../main/webapp/app/entities/game-points';
import { RoundService } from '../../../../../../main/webapp/app/entities/round';

describe('Component Tests', () => {

    describe('Game Management Dialog Component', () => {
        let comp: GameDialogComponent;
        let fixture: ComponentFixture<GameDialogComponent>;
        let service: GameService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GaffelCupTestModule],
                declarations: [GameDialogComponent],
                providers: [
                    ContestantService,
                    GamePointsService,
                    RoundService,
                    GameService
                ]
            })
            .overrideTemplate(GameDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GameDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GameService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Game(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.game = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'gameListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Game();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.game = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'gameListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
