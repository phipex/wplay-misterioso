import { BaseEntity } from './../../shared';

export class Ticket implements BaseEntity {
    constructor(
        public id?: number,
        public id_fuente?: string,
        public cantidad_apuestas?: number,
        public valor_total?: number,
        public fecha?: any,
        public participa_misterioso?: string,
    ) {
    }
}
