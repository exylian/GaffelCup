import { BaseEntity } from './../../shared';

export const enum Status {
    'OPEN',
    'CLOSED'
}

export class Game implements BaseEntity {
    constructor(
        public id?: number,
        public status?: Status,
        public calculated?: boolean,
        public t1P1?: BaseEntity,
        public t1P2?: BaseEntity,
        public t2P1?: BaseEntity,
        public t2P2?: BaseEntity,
        public points?: BaseEntity,
        public round?: BaseEntity,
    ) {
        this.calculated = false;
    }
}
