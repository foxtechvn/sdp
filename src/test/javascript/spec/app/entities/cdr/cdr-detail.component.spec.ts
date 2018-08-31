/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SdpTestModule } from '../../../test.module';
import { CdrDetailComponent } from 'app/entities/cdr/cdr-detail.component';
import { Cdr } from 'app/shared/model/cdr.model';

describe('Component Tests', () => {
    describe('Cdr Management Detail Component', () => {
        let comp: CdrDetailComponent;
        let fixture: ComponentFixture<CdrDetailComponent>;
        const route = ({ data: of({ cdr: new Cdr('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SdpTestModule],
                declarations: [CdrDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CdrDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CdrDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cdr).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
