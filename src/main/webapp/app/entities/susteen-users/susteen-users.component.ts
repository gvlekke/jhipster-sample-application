import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISusteenUsers } from 'app/shared/model/susteen-users.model';
import { Principal } from 'app/core';
import { SusteenUsersService } from './susteen-users.service';

@Component({
    selector: 'jhi-susteen-users',
    templateUrl: './susteen-users.component.html'
})
export class SusteenUsersComponent implements OnInit, OnDestroy {
    susteenUsers: ISusteenUsers[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private susteenUsersService: SusteenUsersService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.susteenUsersService.query().subscribe(
            (res: HttpResponse<ISusteenUsers[]>) => {
                this.susteenUsers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSusteenUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISusteenUsers) {
        return item.id;
    }

    registerChangeInSusteenUsers() {
        this.eventSubscriber = this.eventManager.subscribe('susteenUsersListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
