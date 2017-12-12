/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { GaffelCupTestModule } from '../../../test.module';
import { ContestantComponent } from '../../../../../../main/webapp/app/entities/contestant/contestant.component';
import { ContestantService } from '../../../../../../main/webapp/app/entities/contestant/contestant.service';
import { Contestant } from '../../../../../../main/webapp/app/entities/contestant/contestant.model';

describe('Component Tests', () => {

    describe('Contestant Management Component', () => {
        let comp: ContestantComponent;
        let fixture: ComponentFixture<ContestantComponent>;
        let service: ContestantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GaffelCupTestModule],
                declarations: [ContestantComponent],
                providers: [
                    ContestantService
                ]
            })
            .overrideTemplate(ContestantComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContestantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContestantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Contestant(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.contestants[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
