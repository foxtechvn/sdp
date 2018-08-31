/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SdpTestModule } from '../../../test.module';
import { SmsContentDetailComponent } from 'app/entities/sms-content/sms-content-detail.component';
import { SmsContent } from 'app/shared/model/sms-content.model';

describe('Component Tests', () => {
    describe('SmsContent Management Detail Component', () => {
        let comp: SmsContentDetailComponent;
        let fixture: ComponentFixture<SmsContentDetailComponent>;
        const route = ({ data: of({ smsContent: new SmsContent('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SdpTestModule],
                declarations: [SmsContentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SmsContentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SmsContentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.smsContent).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
