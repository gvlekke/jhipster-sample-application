import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ITemplates } from 'app/shared/model/templates.model';
import { TemplatesService } from './templates.service';
import { ISusteenUsers } from 'app/shared/model/susteen-users.model';
import { SusteenUsersService } from 'app/entities/susteen-users';

@Component({
    selector: 'jhi-templates-update',
    templateUrl: './templates-update.component.html'
})
export class TemplatesUpdateComponent implements OnInit {
    templates: ITemplates;
    isSaving: boolean;

    susteenusers: ISusteenUsers[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private templatesService: TemplatesService,
        private susteenUsersService: SusteenUsersService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ templates }) => {
            this.templates = templates;
        });
        this.susteenUsersService.query().subscribe(
            (res: HttpResponse<ISusteenUsers[]>) => {
                this.susteenusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.templates.id !== undefined) {
            this.subscribeToSaveResponse(this.templatesService.update(this.templates));
        } else {
            this.subscribeToSaveResponse(this.templatesService.create(this.templates));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITemplates>>) {
        result.subscribe((res: HttpResponse<ITemplates>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSusteenUsersById(index: number, item: ISusteenUsers) {
        return item.id;
    }
}
