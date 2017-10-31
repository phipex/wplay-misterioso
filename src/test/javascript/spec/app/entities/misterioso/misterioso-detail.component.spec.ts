/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WplayMisteriosoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MisteriosoDetailComponent } from '../../../../../../main/webapp/app/entities/misterioso/misterioso-detail.component';
import { MisteriosoService } from '../../../../../../main/webapp/app/entities/misterioso/misterioso.service';
import { Misterioso } from '../../../../../../main/webapp/app/entities/misterioso/misterioso.model';

describe('Component Tests', () => {

    describe('Misterioso Management Detail Component', () => {
        let comp: MisteriosoDetailComponent;
        let fixture: ComponentFixture<MisteriosoDetailComponent>;
        let service: MisteriosoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WplayMisteriosoTestModule],
                declarations: [MisteriosoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MisteriosoService,
                    JhiEventManager
                ]
            }).overrideTemplate(MisteriosoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MisteriosoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MisteriosoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Misterioso(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.misterioso).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
