import { BaseEntity } from './../../shared';

export const enum Strength {
    'LOW',
    'LOWMID',
    'MID',
    'MIDHIGH',
    'HIGH'
}

export class Contestant implements BaseEntity {
    constructor(
        public id?: number,
        public lastName?: string,
        public firstName?: string,
        public eMail?: string,
        public strength?: Strength,
        public totalPoints?: number,
        public confirmed?: boolean,
        public confirmedAt?: any,
        public payed?: boolean,
        public payedAt?: any,
        public token?: string,
    ) {
        this.confirmed = false;
        this.payed = false;
    }
}
