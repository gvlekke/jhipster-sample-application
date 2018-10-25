import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    ClientsComponent,
    ClientsDetailComponent,
    ClientsUpdateComponent,
    ClientsDeletePopupComponent,
    ClientsDeleteDialogComponent,
    clientsRoute,
    clientsPopupRoute
} from './';

const ENTITY_STATES = [...clientsRoute, ...clientsPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClientsComponent,
        ClientsDetailComponent,
        ClientsUpdateComponent,
        ClientsDeleteDialogComponent,
        ClientsDeletePopupComponent
    ],
    entryComponents: [ClientsComponent, ClientsUpdateComponent, ClientsDeleteDialogComponent, ClientsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationClientsModule {}
