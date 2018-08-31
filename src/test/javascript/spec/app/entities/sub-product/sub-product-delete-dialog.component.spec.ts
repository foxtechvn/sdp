/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SdpTestModule } from '../../../test.module';
import { SubProductDeleteDialogComponent } from 'app/entities/sub-product/sub-product-delete-dialog.component';
import { SubProductService } from 'app/entities/sub-product/sub-product.service';

describe('Component Tests', () => {
    describe('SubProduct Management Delete Component', () => {
        let comp: SubProductDeleteDialogComponent;
        let fixture: ComponentFixture<SubProductDeleteDialogComponent>;
        let service: SubProductService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SdpTestModule],
                declarations: [SubProductDeleteDialogComponent]
            })
                .overrideTemplate(SubProductDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SubProductDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubProductService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete('123');
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith('123');
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
