/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SusteenUsersDetailComponent } from 'app/entities/susteen-users/susteen-users-detail.component';
import { SusteenUsers } from 'app/shared/model/susteen-users.model';

describe('Component Tests', () => {
    describe('SusteenUsers Management Detail Component', () => {
        let comp: SusteenUsersDetailComponent;
        let fixture: ComponentFixture<SusteenUsersDetailComponent>;
        const route = ({ data: of({ susteenUsers: new SusteenUsers(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [SusteenUsersDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SusteenUsersDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SusteenUsersDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.susteenUsers).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
