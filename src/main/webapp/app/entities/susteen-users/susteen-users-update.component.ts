import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISusteenUsers } from 'app/shared/model/susteen-users.model';
import { SusteenUsersService } from './susteen-users.service';
import { IClients } from 'app/shared/model/clients.model';
import { ClientsService } from 'app/entities/clients';

@Component({
    selector: 'jhi-susteen-users-update',
    templateUrl: './susteen-users-update.component.html'
})
export class SusteenUsersUpdateComponent implements OnInit {
    susteenUsers: ISusteenUsers;
    isSaving: boolean;

    clients: IClients[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private susteenUsersService: SusteenUsersService,
        private clientsService: ClientsService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ susteenUsers }) => {
            this.susteenUsers = susteenUsers;
        });
        this.clientsService.query().subscribe(
            (res: HttpResponse<IClients[]>) => {
                this.clients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.susteenUsers.id !== undefined) {
            this.subscribeToSaveResponse(this.susteenUsersService.update(this.susteenUsers));
        } else {
            this.subscribeToSaveResponse(this.susteenUsersService.create(this.susteenUsers));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISusteenUsers>>) {
        result.subscribe((res: HttpResponse<ISusteenUsers>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackClientsById(index: number, item: IClients) {
        return item.id;
    }
}
