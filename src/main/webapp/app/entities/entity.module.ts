import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TastFondationCandidatModule } from './candidat/candidat.module';
import { TastFondationCandidatureModule } from './candidature/candidature.module';
import { TastFondationProjetModule } from './projet/projet.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TastFondationCandidatModule,
        TastFondationCandidatureModule,
        TastFondationProjetModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TastFondationEntityModule {}
