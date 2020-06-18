import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemoNgTestModule } from '../../../test.module';
import { AttrAndValueDetailComponent } from 'app/entities/attr-and-value/attr-and-value-detail.component';
import { AttrAndValue } from 'app/shared/model/attr-and-value.model';

describe('Component Tests', () => {
  describe('AttrAndValue Management Detail Component', () => {
    let comp: AttrAndValueDetailComponent;
    let fixture: ComponentFixture<AttrAndValueDetailComponent>;
    const route = ({ data: of({ attrAndValue: new AttrAndValue(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DemoNgTestModule],
        declarations: [AttrAndValueDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AttrAndValueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttrAndValueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load attrAndValue on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.attrAndValue).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
