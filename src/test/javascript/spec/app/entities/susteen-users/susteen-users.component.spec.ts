/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SusteenUsersComponent } from 'app/entities/susteen-users/susteen-users.component';
import { SusteenUsersService } from 'app/entities/susteen-users/susteen-users.service';
import { SusteenUsers } from 'app/shared/model/susteen-users.model';

describe('Component Tests', () => {
    describe('SusteenUsers Management Component', () => {
        let comp: SusteenUsersComponent;
        let fixture: ComponentFixture<SusteenUsersComponent>;
        let service: SusteenUsersService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [SusteenUsersComponent],
                providers: []
            })
                .overrideTemplate(SusteenUsersComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SusteenUsersComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SusteenUsersService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SusteenUsers(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.susteenUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
