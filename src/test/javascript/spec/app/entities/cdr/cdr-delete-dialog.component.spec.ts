/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SdpTestModule } from '../../../test.module';
import { CdrDeleteDialogComponent } from 'app/entities/cdr/cdr-delete-dialog.component';
import { CdrService } from 'app/entities/cdr/cdr.service';

describe('Component Tests', () => {
    describe('Cdr Management Delete Component', () => {
        let comp: CdrDeleteDialogComponent;
        let fixture: ComponentFixture<CdrDeleteDialogComponent>;
        let service: CdrService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SdpTestModule],
                declarations: [CdrDeleteDialogComponent]
            })
                .overrideTemplate(CdrDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CdrDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CdrService);
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
