import { BaseEntity } from './../../shared';

export class Projet implements BaseEntity {
    constructor(
        public id?: number,
        public intitule?: string,
        public montant?: string,
        public domaine?: string,
        public type?: string,
        public candidature?: BaseEntity,
    ) {
    }
}
