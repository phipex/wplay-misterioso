import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Ticket } from './ticket.model';
import { TicketService } from './ticket.service';

@Component({
    selector: 'jhi-ticket-detail',
    templateUrl: './ticket-detail.component.html'
})
export class TicketDetailComponent implements OnInit, OnDestroy {

    ticket: Ticket;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ticketService: TicketService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTickets();
    }

    load(id) {
        this.ticketService.find(id).subscribe((ticket) => {
            this.ticket = ticket;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTickets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ticketListModification',
            (response) => this.load(this.ticket.id)
        );
    }
}
