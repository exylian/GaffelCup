import { BaseEntity } from './../../shared';

export const enum Status {
    'OPEN',
    'CLOSED'
}

export class Round implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public status?: Status,
        public finishedAt?: any,
    ) {
    }
}
