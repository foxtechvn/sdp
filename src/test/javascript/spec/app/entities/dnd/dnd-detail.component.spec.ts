/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SdpTestModule } from '../../../test.module';
import { DndDetailComponent } from 'app/entities/dnd/dnd-detail.component';
import { Dnd } from 'app/shared/model/dnd.model';

describe('Component Tests', () => {
    describe('Dnd Management Detail Component', () => {
        let comp: DndDetailComponent;
        let fixture: ComponentFixture<DndDetailComponent>;
        const route = ({ data: of({ dnd: new Dnd('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SdpTestModule],
                declarations: [DndDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DndDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DndDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dnd).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
