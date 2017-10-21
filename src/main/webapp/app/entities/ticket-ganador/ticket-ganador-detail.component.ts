import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TicketGanador } from './ticket-ganador.model';
import { TicketGanadorService } from './ticket-ganador.service';

@Component({
    selector: 'jhi-ticket-ganador-detail',
    templateUrl: './ticket-ganador-detail.component.html'
})
export class TicketGanadorDetailComponent implements OnInit, OnDestroy {

    ticketGanador: TicketGanador;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ticketGanadorService: TicketGanadorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTicketGanadors();
    }

    load(id) {
        this.ticketGanadorService.find(id).subscribe((ticketGanador) => {
            this.ticketGanador = ticketGanador;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTicketGanadors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ticketGanadorListModification',
            (response) => this.load(this.ticketGanador.id)
        );
    }
}
