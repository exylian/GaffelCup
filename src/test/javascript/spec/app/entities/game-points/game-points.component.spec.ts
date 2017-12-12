/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { GaffelCupTestModule } from '../../../test.module';
import { GamePointsComponent } from '../../../../../../main/webapp/app/entities/game-points/game-points.component';
import { GamePointsService } from '../../../../../../main/webapp/app/entities/game-points/game-points.service';
import { GamePoints } from '../../../../../../main/webapp/app/entities/game-points/game-points.model';

describe('Component Tests', () => {

    describe('GamePoints Management Component', () => {
        let comp: GamePointsComponent;
        let fixture: ComponentFixture<GamePointsComponent>;
        let service: GamePointsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GaffelCupTestModule],
                declarations: [GamePointsComponent],
                providers: [
                    GamePointsService
                ]
            })
            .overrideTemplate(GamePointsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GamePointsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GamePointsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new GamePoints(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.gamePoints[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
