/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SdpTestModule } from '../../../test.module';
import { DndUpdateComponent } from 'app/entities/dnd/dnd-update.component';
import { DndService } from 'app/entities/dnd/dnd.service';
import { Dnd } from 'app/shared/model/dnd.model';

describe('Component Tests', () => {
    describe('Dnd Management Update Component', () => {
        let comp: DndUpdateComponent;
        let fixture: ComponentFixture<DndUpdateComponent>;
        let service: DndService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SdpTestModule],
                declarations: [DndUpdateComponent]
            })
                .overrideTemplate(DndUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DndUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DndService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Dnd('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dnd = entity;
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
                    const entity = new Dnd();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dnd = entity;
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
