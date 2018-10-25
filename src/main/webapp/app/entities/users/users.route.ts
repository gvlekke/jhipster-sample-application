import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Users } from 'app/shared/model/users.model';
import { UsersService } from './users.service';
import { UsersComponent } from './users.component';
import { UsersDetailComponent } from './users-detail.component';
import { UsersUpdateComponent } from './users-update.component';
import { UsersDeletePopupComponent } from './users-delete-dialog.component';
import { IUsers } from 'app/shared/model/users.model';

@Injectable({ providedIn: 'root' })
export class UsersResolve implements Resolve<IUsers> {
    constructor(private service: UsersService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((users: HttpResponse<Users>) => users.body));
        }
        return of(new Users());
    }
}

export const usersRoute: Routes = [
    {
        path: 'users',
        component: UsersComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.users.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'users/:id/view',
        component: UsersDetailComponent,
        resolve: {
            users: UsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.users.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'users/new',
        component: UsersUpdateComponent,
        resolve: {
            users: UsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.users.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'users/:id/edit',
        component: UsersUpdateComponent,
        resolve: {
            users: UsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.users.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const usersPopupRoute: Routes = [
    {
        path: 'users/:id/delete',
        component: UsersDeletePopupComponent,
        resolve: {
            users: UsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.users.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
