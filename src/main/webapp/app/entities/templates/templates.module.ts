import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    TemplatesComponent,
    TemplatesDetailComponent,
    TemplatesUpdateComponent,
    TemplatesDeletePopupComponent,
    TemplatesDeleteDialogComponent,
    templatesRoute,
    templatesPopupRoute
} from './';

const ENTITY_STATES = [...templatesRoute, ...templatesPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TemplatesComponent,
        TemplatesDetailComponent,
        TemplatesUpdateComponent,
        TemplatesDeleteDialogComponent,
        TemplatesDeletePopupComponent
    ],
    entryComponents: [TemplatesComponent, TemplatesUpdateComponent, TemplatesDeleteDialogComponent, TemplatesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTemplatesModule {}
