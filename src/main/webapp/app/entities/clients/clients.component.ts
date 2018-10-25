import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IClients } from 'app/shared/model/clients.model';
import { Principal } from 'app/core';
import { ClientsService } from './clients.service';

@Component({
    selector: 'jhi-clients',
    templateUrl: './clients.component.html'
})
export class ClientsComponent implements OnInit, OnDestroy {
    clients: IClients[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private clientsService: ClientsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.clientsService.query().subscribe(
            (res: HttpResponse<IClients[]>) => {
                this.clients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInClients();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IClients) {
        return item.id;
    }

    registerChangeInClients() {
        this.eventSubscriber = this.eventManager.subscribe('clientsListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
