import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SusteenUsers } from 'app/shared/model/susteen-users.model';
import { SusteenUsersService } from './susteen-users.service';
import { SusteenUsersComponent } from './susteen-users.component';
import { SusteenUsersDetailComponent } from './susteen-users-detail.component';
import { SusteenUsersUpdateComponent } from './susteen-users-update.component';
import { SusteenUsersDeletePopupComponent } from './susteen-users-delete-dialog.component';
import { ISusteenUsers } from 'app/shared/model/susteen-users.model';

@Injectable({ providedIn: 'root' })
export class SusteenUsersResolve implements Resolve<ISusteenUsers> {
    constructor(private service: SusteenUsersService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((susteenUsers: HttpResponse<SusteenUsers>) => susteenUsers.body));
        }
        return of(new SusteenUsers());
    }
}

export const susteenUsersRoute: Routes = [
    {
        path: 'susteen-users',
        component: SusteenUsersComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.susteenUsers.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'susteen-users/:id/view',
        component: SusteenUsersDetailComponent,
        resolve: {
            susteenUsers: SusteenUsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.susteenUsers.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'susteen-users/new',
        component: SusteenUsersUpdateComponent,
        resolve: {
            susteenUsers: SusteenUsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.susteenUsers.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'susteen-users/:id/edit',
        component: SusteenUsersUpdateComponent,
        resolve: {
            susteenUsers: SusteenUsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.susteenUsers.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const susteenUsersPopupRoute: Routes = [
    {
        path: 'susteen-users/:id/delete',
        component: SusteenUsersDeletePopupComponent,
        resolve: {
            susteenUsers: SusteenUsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.susteenUsers.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
