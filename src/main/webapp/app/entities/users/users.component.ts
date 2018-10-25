import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUsers } from 'app/shared/model/users.model';
import { Principal } from 'app/core';
import { UsersService } from './users.service';

@Component({
    selector: 'jhi-users',
    templateUrl: './users.component.html'
})
export class UsersComponent implements OnInit, OnDestroy {
    users: IUsers[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private usersService: UsersService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.usersService.query().subscribe(
            (res: HttpResponse<IUsers[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUsers) {
        return item.id;
    }

    registerChangeInUsers() {
        this.eventSubscriber = this.eventManager.subscribe('usersListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
