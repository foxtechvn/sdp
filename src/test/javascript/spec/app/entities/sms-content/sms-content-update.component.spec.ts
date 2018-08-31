/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SdpTestModule } from '../../../test.module';
import { SmsContentUpdateComponent } from 'app/entities/sms-content/sms-content-update.component';
import { SmsContentService } from 'app/entities/sms-content/sms-content.service';
import { SmsContent } from 'app/shared/model/sms-content.model';

describe('Component Tests', () => {
    describe('SmsContent Management Update Component', () => {
        let comp: SmsContentUpdateComponent;
        let fixture: ComponentFixture<SmsContentUpdateComponent>;
        let service: SmsContentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SdpTestModule],
                declarations: [SmsContentUpdateComponent]
            })
                .overrideTemplate(SmsContentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SmsContentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SmsContentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SmsContent('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.smsContent = entity;
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
                    const entity = new SmsContent();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.smsContent = entity;
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
