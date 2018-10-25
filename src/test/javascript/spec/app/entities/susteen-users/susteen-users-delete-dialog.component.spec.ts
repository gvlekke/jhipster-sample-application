/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SusteenUsersDeleteDialogComponent } from 'app/entities/susteen-users/susteen-users-delete-dialog.component';
import { SusteenUsersService } from 'app/entities/susteen-users/susteen-users.service';

describe('Component Tests', () => {
    describe('SusteenUsers Management Delete Component', () => {
        let comp: SusteenUsersDeleteDialogComponent;
        let fixture: ComponentFixture<SusteenUsersDeleteDialogComponent>;
        let service: SusteenUsersService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [SusteenUsersDeleteDialogComponent]
            })
                .overrideTemplate(SusteenUsersDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SusteenUsersDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SusteenUsersService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
