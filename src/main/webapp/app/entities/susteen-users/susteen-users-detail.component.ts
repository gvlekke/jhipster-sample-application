import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISusteenUsers } from 'app/shared/model/susteen-users.model';

@Component({
    selector: 'jhi-susteen-users-detail',
    templateUrl: './susteen-users-detail.component.html'
})
export class SusteenUsersDetailComponent implements OnInit {
    susteenUsers: ISusteenUsers;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ susteenUsers }) => {
            this.susteenUsers = susteenUsers;
        });
    }

    previousState() {
        window.history.back();
    }
}
