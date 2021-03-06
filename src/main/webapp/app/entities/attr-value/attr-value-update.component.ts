import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAttrValue, AttrValue } from 'app/shared/model/attr-value.model';
import { AttrValueService } from './attr-value.service';

@Component({
  selector: 'jhi-attr-value-update',
  templateUrl: './attr-value-update.component.html'
})
export class AttrValueUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    attrName: [],
    attrValue: [],
    remark: []
  });

  constructor(protected attrValueService: AttrValueService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attrValue }) => {
      this.updateForm(attrValue);
    });
  }

  updateForm(attrValue: IAttrValue): void {
    this.editForm.patchValue({
      id: attrValue.id,
      attrName: attrValue.attrName,
      attrValue: attrValue.attrValue,
      remark: attrValue.remark
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attrValue = this.createFromForm();
    if (attrValue.id !== undefined) {
      this.subscribeToSaveResponse(this.attrValueService.update(attrValue));
    } else {
      this.subscribeToSaveResponse(this.attrValueService.create(attrValue));
    }
  }

  private createFromForm(): IAttrValue {
    return {
      ...new AttrValue(),
      id: this.editForm.get(['id'])!.value,
      attrName: this.editForm.get(['attrName'])!.value,
      attrValue: this.editForm.get(['attrValue'])!.value,
      remark: this.editForm.get(['remark'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttrValue>>): void {
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
