import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Misterioso } from './misterioso.model';
import { MisteriosoService } from './misterioso.service';

@Component({
    selector: 'jhi-misterioso-detail',
    templateUrl: './misterioso-detail.component.html'
})
export class MisteriosoDetailComponent implements OnInit, OnDestroy {

    misterioso: Misterioso;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private misteriosoService: MisteriosoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMisteriosos();
    }

    load(id) {
        this.misteriosoService.find(id).subscribe((misterioso) => {
            this.misterioso = misterioso;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMisteriosos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'misteriosoListModification',
            (response) => this.load(this.misterioso.id)
        );
    }
}
