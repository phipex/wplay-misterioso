import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TicketGanador } from './ticket-ganador.model';
import { TicketGanadorPopupService } from './ticket-ganador-popup.service';
import { TicketGanadorService } from './ticket-ganador.service';
import { Misterioso, MisteriosoService } from '../misterioso';
import { Ticket, TicketService } from '../ticket';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ticket-ganador-dialog',
    templateUrl: './ticket-ganador-dialog.component.html'
})
export class TicketGanadorDialogComponent implements OnInit {

    ticketGanador: TicketGanador;
    isSaving: boolean;

    misteriosos: Misterioso[];

    tickets: Ticket[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ticketGanadorService: TicketGanadorService,
        private misteriosoService: MisteriosoService,
        private ticketService: TicketService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.misteriosoService.query()
            .subscribe((res: ResponseWrapper) => { this.misteriosos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.ticketService
            .query({filter: 'ticketganador-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.ticketGanador.ticketId) {
                    this.tickets = res.json;
                } else {
                    this.ticketService
                        .find(this.ticketGanador.ticketId)
                        .subscribe((subRes: Ticket) => {
                            this.tickets = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ticketGanador.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ticketGanadorService.update(this.ticketGanador));
        } else {
            this.subscribeToSaveResponse(
                this.ticketGanadorService.create(this.ticketGanador));
        }
    }

    private subscribeToSaveResponse(result: Observable<TicketGanador>) {
        result.subscribe((res: TicketGanador) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TicketGanador) {
        this.eventManager.broadcast({ name: 'ticketGanadorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMisteriosoById(index: number, item: Misterioso) {
        return item.id;
    }

    trackTicketById(index: number, item: Ticket) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ticket-ganador-popup',
    template: ''
})
export class TicketGanadorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ticketGanadorPopupService: TicketGanadorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ticketGanadorPopupService
                    .open(TicketGanadorDialogComponent as Component, params['id']);
            } else {
                this.ticketGanadorPopupService
                    .open(TicketGanadorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
