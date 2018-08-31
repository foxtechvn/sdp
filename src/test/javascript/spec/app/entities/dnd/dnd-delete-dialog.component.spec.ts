/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SdpTestModule } from '../../../test.module';
import { DndDeleteDialogComponent } from 'app/entities/dnd/dnd-delete-dialog.component';
import { DndService } from 'app/entities/dnd/dnd.service';

describe('Component Tests', () => {
    describe('Dnd Management Delete Component', () => {
        let comp: DndDeleteDialogComponent;
        let fixture: ComponentFixture<DndDeleteDialogComponent>;
        let service: DndService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SdpTestModule],
                declarations: [DndDeleteDialogComponent]
            })
                .overrideTemplate(DndDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DndDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DndService);
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
