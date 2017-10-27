import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ticket } from './ticket.model';
import { TicketPopupService } from './ticket-popup.service';
import { TicketService } from './ticket.service';

@Component({
    selector: 'jhi-ticket-dialog',
    templateUrl: './ticket-dialog.component.html'
})
export class TicketDialogComponent implements OnInit {

    ticket: Ticket;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private ticketService: TicketService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ticket.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ticketService.update(this.ticket));
        } else {
            this.subscribeToSaveResponse(
                this.ticketService.create(this.ticket));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ticket>) {
        result.subscribe((res: Ticket) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ticket) {
        this.eventManager.broadcast({ name: 'ticketListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-ticket-popup',
    template: ''
})
export class TicketPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ticketPopupService: TicketPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ticketPopupService
                    .open(TicketDialogComponent as Component, params['id']);
            } else {
                this.ticketPopupService
                    .open(TicketDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
