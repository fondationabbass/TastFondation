import { BaseEntity } from './../../shared';

export class Candidat implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public dna?: any,
        public lieuNa?: string,
        public adresse?: string,
        public candidature?: BaseEntity,
    ) {
    }
}
