import { BaseEntity } from './../../shared';

export const enum EstadoMisterioso {
    'ACTIVO',
    'INACTIVO'
}

export class Misterioso implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public acumulado?: number,
        public cantidad_apuestas?: number,
        public porcentaje_ticket?: number,
        public valor_base_min?: number,
        public valor_base_max?: number,
        public minimo_ticket?: number,
        public maximo_ticket?: number,
        public ganador?: string,
        public estado?: EstadoMisterioso,
        public descripcion?: string,
        public cantidad_apuestas_minima?: number,
        public cantidad_apuestas_maxima?: number,
    ) {
    }
}
