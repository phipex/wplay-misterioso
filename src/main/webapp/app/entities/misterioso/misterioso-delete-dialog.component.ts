import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Misterioso } from './misterioso.model';
import { MisteriosoPopupService } from './misterioso-popup.service';
import { MisteriosoService } from './misterioso.service';

@Component({
    selector: 'jhi-misterioso-delete-dialog',
    templateUrl: './misterioso-delete-dialog.component.html'
})
export class MisteriosoDeleteDialogComponent {

    misterioso: Misterioso;

    constructor(
        private misteriosoService: MisteriosoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.misteriosoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'misteriosoListModification',
                content: 'Deleted an misterioso'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-misterioso-delete-popup',
    template: ''
})
export class MisteriosoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private misteriosoPopupService: MisteriosoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.misteriosoPopupService
                .open(MisteriosoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
