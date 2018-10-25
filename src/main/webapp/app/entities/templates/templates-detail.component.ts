import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITemplates } from 'app/shared/model/templates.model';

@Component({
    selector: 'jhi-templates-detail',
    templateUrl: './templates-detail.component.html'
})
export class TemplatesDetailComponent implements OnInit {
    templates: ITemplates;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ templates }) => {
            this.templates = templates;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
