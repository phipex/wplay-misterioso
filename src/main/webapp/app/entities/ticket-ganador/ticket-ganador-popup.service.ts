import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { TicketGanador } from './ticket-ganador.model';
import { TicketGanadorService } from './ticket-ganador.service';

@Injectable()
export class TicketGanadorPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private ticketGanadorService: TicketGanadorService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.ticketGanadorService.find(id).subscribe((ticketGanador) => {
                    ticketGanador.fecha = this.datePipe
                        .transform(ticketGanador.fecha, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.ticketGanadorModalRef(component, ticketGanador);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ticketGanadorModalRef(component, new TicketGanador());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ticketGanadorModalRef(component: Component, ticketGanador: TicketGanador): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ticketGanador = ticketGanador;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
