/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WplayMisteriosoTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TicketGanadorDetailComponent } from '../../../../../../main/webapp/app/entities/ticket-ganador/ticket-ganador-detail.component';
import { TicketGanadorService } from '../../../../../../main/webapp/app/entities/ticket-ganador/ticket-ganador.service';
import { TicketGanador } from '../../../../../../main/webapp/app/entities/ticket-ganador/ticket-ganador.model';

describe('Component Tests', () => {

    describe('TicketGanador Management Detail Component', () => {
        let comp: TicketGanadorDetailComponent;
        let fixture: ComponentFixture<TicketGanadorDetailComponent>;
        let service: TicketGanadorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WplayMisteriosoTestModule],
                declarations: [TicketGanadorDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TicketGanadorService,
                    JhiEventManager
                ]
            }).overrideTemplate(TicketGanadorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TicketGanadorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TicketGanadorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TicketGanador(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ticketGanador).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
