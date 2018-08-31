/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SdpTestModule } from '../../../test.module';
import { SubProductUpdateComponent } from 'app/entities/sub-product/sub-product-update.component';
import { SubProductService } from 'app/entities/sub-product/sub-product.service';
import { SubProduct } from 'app/shared/model/sub-product.model';

describe('Component Tests', () => {
    describe('SubProduct Management Update Component', () => {
        let comp: SubProductUpdateComponent;
        let fixture: ComponentFixture<SubProductUpdateComponent>;
        let service: SubProductService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SdpTestModule],
                declarations: [SubProductUpdateComponent]
            })
                .overrideTemplate(SubProductUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SubProductUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubProductService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SubProduct('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.subProduct = entity;
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
                    const entity = new SubProduct();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.subProduct = entity;
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
