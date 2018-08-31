/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SdpTestModule } from '../../../test.module';
import { SmsContentDeleteDialogComponent } from 'app/entities/sms-content/sms-content-delete-dialog.component';
import { SmsContentService } from 'app/entities/sms-content/sms-content.service';

describe('Component Tests', () => {
    describe('SmsContent Management Delete Component', () => {
        let comp: SmsContentDeleteDialogComponent;
        let fixture: ComponentFixture<SmsContentDeleteDialogComponent>;
        let service: SmsContentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SdpTestModule],
                declarations: [SmsContentDeleteDialogComponent]
            })
                .overrideTemplate(SmsContentDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SmsContentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SmsContentService);
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
