import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationClientsModule } from './clients/clients.module';
import { JhipsterSampleApplicationSusteenUsersModule } from './susteen-users/susteen-users.module';
import { JhipsterSampleApplicationTemplatesModule } from './templates/templates.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterSampleApplicationClientsModule,
        JhipsterSampleApplicationSusteenUsersModule,
        JhipsterSampleApplicationTemplatesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
