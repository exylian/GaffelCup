import { BaseEntity } from './../../shared';

export class GamePoints implements BaseEntity {
    constructor(
        public id?: number,
        public s1T1?: number,
        public s1T2?: number,
        public s2T1?: number,
        public s2T2?: number,
        public s3T1?: number,
        public s3T2?: number,
    ) {
    }
}
