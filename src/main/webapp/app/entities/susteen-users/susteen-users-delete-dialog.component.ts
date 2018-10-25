import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISusteenUsers } from 'app/shared/model/susteen-users.model';
import { SusteenUsersService } from './susteen-users.service';

@Component({
    selector: 'jhi-susteen-users-delete-dialog',
    templateUrl: './susteen-users-delete-dialog.component.html'
})
export class SusteenUsersDeleteDialogComponent {
    susteenUsers: ISusteenUsers;

    constructor(
        private susteenUsersService: SusteenUsersService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.susteenUsersService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'susteenUsersListModification',
                content: 'Deleted an susteenUsers'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-susteen-users-delete-popup',
    template: ''
})
export class SusteenUsersDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ susteenUsers }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SusteenUsersDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.susteenUsers = susteenUsers;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
