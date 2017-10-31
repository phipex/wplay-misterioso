import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TicketGanador } from './ticket-ganador.model';
import { TicketGanadorPopupService } from './ticket-ganador-popup.service';
import { TicketGanadorService } from './ticket-ganador.service';

@Component({
    selector: 'jhi-ticket-ganador-delete-dialog',
    templateUrl: './ticket-ganador-delete-dialog.component.html'
})
export class TicketGanadorDeleteDialogComponent {

    ticketGanador: TicketGanador;

    constructor(
        private ticketGanadorService: TicketGanadorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ticketGanadorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ticketGanadorListModification',
                content: 'Deleted an ticketGanador'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ticket-ganador-delete-popup',
    template: ''
})
export class TicketGanadorDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ticketGanadorPopupService: TicketGanadorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ticketGanadorPopupService
                .open(TicketGanadorDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
