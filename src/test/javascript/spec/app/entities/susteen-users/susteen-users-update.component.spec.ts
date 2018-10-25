/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SusteenUsersUpdateComponent } from 'app/entities/susteen-users/susteen-users-update.component';
import { SusteenUsersService } from 'app/entities/susteen-users/susteen-users.service';
import { SusteenUsers } from 'app/shared/model/susteen-users.model';

describe('Component Tests', () => {
    describe('SusteenUsers Management Update Component', () => {
        let comp: SusteenUsersUpdateComponent;
        let fixture: ComponentFixture<SusteenUsersUpdateComponent>;
        let service: SusteenUsersService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [SusteenUsersUpdateComponent]
            })
                .overrideTemplate(SusteenUsersUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SusteenUsersUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SusteenUsersService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SusteenUsers(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.susteenUsers = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SusteenUsers();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.susteenUsers = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
