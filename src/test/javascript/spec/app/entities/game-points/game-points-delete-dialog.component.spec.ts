/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { GaffelCupTestModule } from '../../../test.module';
import { GamePointsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/game-points/game-points-delete-dialog.component';
import { GamePointsService } from '../../../../../../main/webapp/app/entities/game-points/game-points.service';

describe('Component Tests', () => {

    describe('GamePoints Management Delete Component', () => {
        let comp: GamePointsDeleteDialogComponent;
        let fixture: ComponentFixture<GamePointsDeleteDialogComponent>;
        let service: GamePointsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GaffelCupTestModule],
                declarations: [GamePointsDeleteDialogComponent],
                providers: [
                    GamePointsService
                ]
            })
            .overrideTemplate(GamePointsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GamePointsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GamePointsService);
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
