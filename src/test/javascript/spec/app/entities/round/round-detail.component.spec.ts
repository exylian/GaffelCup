/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { GaffelCupTestModule } from '../../../test.module';
import { RoundDetailComponent } from '../../../../../../main/webapp/app/entities/round/round-detail.component';
import { RoundService } from '../../../../../../main/webapp/app/entities/round/round.service';
import { Round } from '../../../../../../main/webapp/app/entities/round/round.model';

describe('Component Tests', () => {

    describe('Round Management Detail Component', () => {
        let comp: RoundDetailComponent;
        let fixture: ComponentFixture<RoundDetailComponent>;
        let service: RoundService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GaffelCupTestModule],
                declarations: [RoundDetailComponent],
                providers: [
                    RoundService
                ]
            })
            .overrideTemplate(RoundDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RoundDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoundService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Round(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.round).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
