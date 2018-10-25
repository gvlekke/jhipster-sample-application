import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Templates } from 'app/shared/model/templates.model';
import { TemplatesService } from './templates.service';
import { TemplatesComponent } from './templates.component';
import { TemplatesDetailComponent } from './templates-detail.component';
import { TemplatesUpdateComponent } from './templates-update.component';
import { TemplatesDeletePopupComponent } from './templates-delete-dialog.component';
import { ITemplates } from 'app/shared/model/templates.model';

@Injectable({ providedIn: 'root' })
export class TemplatesResolve implements Resolve<ITemplates> {
    constructor(private service: TemplatesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((templates: HttpResponse<Templates>) => templates.body));
        }
        return of(new Templates());
    }
}

export const templatesRoute: Routes = [
    {
        path: 'templates',
        component: TemplatesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.templates.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'templates/:id/view',
        component: TemplatesDetailComponent,
        resolve: {
            templates: TemplatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.templates.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'templates/new',
        component: TemplatesUpdateComponent,
        resolve: {
            templates: TemplatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.templates.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'templates/:id/edit',
        component: TemplatesUpdateComponent,
        resolve: {
            templates: TemplatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.templates.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const templatesPopupRoute: Routes = [
    {
        path: 'templates/:id/delete',
        component: TemplatesDeletePopupComponent,
        resolve: {
            templates: TemplatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.templates.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
