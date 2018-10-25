/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TemplatesComponent } from 'app/entities/templates/templates.component';
import { TemplatesService } from 'app/entities/templates/templates.service';
import { Templates } from 'app/shared/model/templates.model';

describe('Component Tests', () => {
    describe('Templates Management Component', () => {
        let comp: TemplatesComponent;
        let fixture: ComponentFixture<TemplatesComponent>;
        let service: TemplatesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TemplatesComponent],
                providers: []
            })
                .overrideTemplate(TemplatesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TemplatesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TemplatesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Templates(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.templates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
