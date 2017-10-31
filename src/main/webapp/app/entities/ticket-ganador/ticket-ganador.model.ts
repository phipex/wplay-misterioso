import { BaseEntity } from './../../shared';

export const enum EstadoGanador {
    'PENDIENTE',
    'GANADO'
}

export class TicketGanador implements BaseEntity {
    constructor(
        public id?: number,
        public estado?: EstadoGanador,
        public valor_ganado?: number,
        public fecha?: any,
        public descripcion?: string,
        public indice_ticket_misterioso?: number,
        public misteriosoId?: number,
        public ticketId?: number,
    ) {
    }
}
