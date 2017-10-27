import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Misterioso } from './misterioso.model';
import { MisteriosoPopupService } from './misterioso-popup.service';
import { MisteriosoService } from './misterioso.service';

@Component({
    selector: 'jhi-misterioso-dialog',
    templateUrl: './misterioso-dialog.component.html'
})
export class MisteriosoDialogComponent implements OnInit {

    misterioso: Misterioso;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private misteriosoService: MisteriosoService,
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
        if (this.misterioso.id !== undefined) {
            this.subscribeToSaveResponse(
                this.misteriosoService.update(this.misterioso));
        } else {
            this.subscribeToSaveResponse(
                this.misteriosoService.create(this.misterioso));
        }
    }

    private subscribeToSaveResponse(result: Observable<Misterioso>) {
        result.subscribe((res: Misterioso) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Misterioso) {
        this.eventManager.broadcast({ name: 'misteriosoListModification', content: 'OK'});
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
    selector: 'jhi-misterioso-popup',
    template: ''
})
export class MisteriosoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private misteriosoPopupService: MisteriosoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.misteriosoPopupService
                    .open(MisteriosoDialogComponent as Component, params['id']);
            } else {
                this.misteriosoPopupService
                    .open(MisteriosoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
