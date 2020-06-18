import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAttrAndValue, AttrAndValue } from 'app/shared/model/attr-and-value.model';
import { AttrAndValueService } from './attr-and-value.service';

@Component({
  selector: 'jhi-attr-and-value-update',
  templateUrl: './attr-and-value-update.component.html'
})
export class AttrAndValueUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    attrName: [],
    attrValue: []
  });

  constructor(protected attrAndValueService: AttrAndValueService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attrAndValue }) => {
      this.updateForm(attrAndValue);
    });
  }

  updateForm(attrAndValue: IAttrAndValue): void {
    this.editForm.patchValue({
      id: attrAndValue.id,
      attrName: attrAndValue.attrName,
      attrValue: attrAndValue.attrValue
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attrAndValue = this.createFromForm();
    if (attrAndValue.id !== undefined) {
      this.subscribeToSaveResponse(this.attrAndValueService.update(attrAndValue));
    } else {
      this.subscribeToSaveResponse(this.attrAndValueService.create(attrAndValue));
    }
  }

  private createFromForm(): IAttrAndValue {
    return {
      ...new AttrAndValue(),
      id: this.editForm.get(['id'])!.value,
      attrName: this.editForm.get(['attrName'])!.value,
      attrValue: this.editForm.get(['attrValue'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttrAndValue>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
