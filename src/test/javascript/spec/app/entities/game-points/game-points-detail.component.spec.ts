/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { GaffelCupTestModule } from '../../../test.module';
import { GamePointsDetailComponent } from '../../../../../../main/webapp/app/entities/game-points/game-points-detail.component';
import { GamePointsService } from '../../../../../../main/webapp/app/entities/game-points/game-points.service';
import { GamePoints } from '../../../../../../main/webapp/app/entities/game-points/game-points.model';

describe('Component Tests', () => {

    describe('GamePoints Management Detail Component', () => {
        let comp: GamePointsDetailComponent;
        let fixture: ComponentFixture<GamePointsDetailComponent>;
        let service: GamePointsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GaffelCupTestModule],
                declarations: [GamePointsDetailComponent],
                providers: [
                    GamePointsService
                ]
            })
            .overrideTemplate(GamePointsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GamePointsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GamePointsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new GamePoints(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.gamePoints).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
