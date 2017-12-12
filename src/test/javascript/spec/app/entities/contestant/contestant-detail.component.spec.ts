/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { GaffelCupTestModule } from '../../../test.module';
import { ContestantDetailComponent } from '../../../../../../main/webapp/app/entities/contestant/contestant-detail.component';
import { ContestantService } from '../../../../../../main/webapp/app/entities/contestant/contestant.service';
import { Contestant } from '../../../../../../main/webapp/app/entities/contestant/contestant.model';

describe('Component Tests', () => {

    describe('Contestant Management Detail Component', () => {
        let comp: ContestantDetailComponent;
        let fixture: ComponentFixture<ContestantDetailComponent>;
        let service: ContestantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GaffelCupTestModule],
                declarations: [ContestantDetailComponent],
                providers: [
                    ContestantService
                ]
            })
            .overrideTemplate(ContestantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContestantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContestantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Contestant(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.contestant).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
