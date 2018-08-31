/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SdpTestModule } from '../../../test.module';
import { CdrUpdateComponent } from 'app/entities/cdr/cdr-update.component';
import { CdrService } from 'app/entities/cdr/cdr.service';
import { Cdr } from 'app/shared/model/cdr.model';

describe('Component Tests', () => {
    describe('Cdr Management Update Component', () => {
        let comp: CdrUpdateComponent;
        let fixture: ComponentFixture<CdrUpdateComponent>;
        let service: CdrService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SdpTestModule],
                declarations: [CdrUpdateComponent]
            })
                .overrideTemplate(CdrUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CdrUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CdrService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Cdr('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cdr = entity;
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
                    const entity = new Cdr();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cdr = entity;
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
