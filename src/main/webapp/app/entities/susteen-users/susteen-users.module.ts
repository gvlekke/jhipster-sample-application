import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    SusteenUsersComponent,
    SusteenUsersDetailComponent,
    SusteenUsersUpdateComponent,
    SusteenUsersDeletePopupComponent,
    SusteenUsersDeleteDialogComponent,
    susteenUsersRoute,
    susteenUsersPopupRoute
} from './';

const ENTITY_STATES = [...susteenUsersRoute, ...susteenUsersPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SusteenUsersComponent,
        SusteenUsersDetailComponent,
        SusteenUsersUpdateComponent,
        SusteenUsersDeleteDialogComponent,
        SusteenUsersDeletePopupComponent
    ],
    entryComponents: [
        SusteenUsersComponent,
        SusteenUsersUpdateComponent,
        SusteenUsersDeleteDialogComponent,
        SusteenUsersDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationSusteenUsersModule {}
