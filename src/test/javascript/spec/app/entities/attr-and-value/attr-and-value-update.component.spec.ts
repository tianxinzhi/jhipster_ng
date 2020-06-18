import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DemoNgTestModule } from '../../../test.module';
import { AttrAndValueUpdateComponent } from 'app/entities/attr-and-value/attr-and-value-update.component';
import { AttrAndValueService } from 'app/entities/attr-and-value/attr-and-value.service';
import { AttrAndValue } from 'app/shared/model/attr-and-value.model';

describe('Component Tests', () => {
  describe('AttrAndValue Management Update Component', () => {
    let comp: AttrAndValueUpdateComponent;
    let fixture: ComponentFixture<AttrAndValueUpdateComponent>;
    let service: AttrAndValueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DemoNgTestModule],
        declarations: [AttrAndValueUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AttrAndValueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttrAndValueUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttrAndValueService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttrAndValue(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttrAndValue();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
