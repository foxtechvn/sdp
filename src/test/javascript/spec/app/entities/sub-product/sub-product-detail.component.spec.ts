/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SdpTestModule } from '../../../test.module';
import { SubProductDetailComponent } from 'app/entities/sub-product/sub-product-detail.component';
import { SubProduct } from 'app/shared/model/sub-product.model';

describe('Component Tests', () => {
    describe('SubProduct Management Detail Component', () => {
        let comp: SubProductDetailComponent;
        let fixture: ComponentFixture<SubProductDetailComponent>;
        const route = ({ data: of({ subProduct: new SubProduct('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SdpTestModule],
                declarations: [SubProductDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SubProductDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SubProductDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.subProduct).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
