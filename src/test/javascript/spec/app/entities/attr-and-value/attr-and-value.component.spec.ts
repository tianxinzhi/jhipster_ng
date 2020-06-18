import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DemoNgTestModule } from '../../../test.module';
import { AttrAndValueComponent } from 'app/entities/attr-and-value/attr-and-value.component';
import { AttrAndValueService } from 'app/entities/attr-and-value/attr-and-value.service';
import { AttrAndValue } from 'app/shared/model/attr-and-value.model';

describe('Component Tests', () => {
  describe('AttrAndValue Management Component', () => {
    let comp: AttrAndValueComponent;
    let fixture: ComponentFixture<AttrAndValueComponent>;
    let service: AttrAndValueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DemoNgTestModule],
        declarations: [AttrAndValueComponent]
      })
        .overrideTemplate(AttrAndValueComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttrAndValueComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttrAndValueService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AttrAndValue(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.attrAndValues && comp.attrAndValues[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
