import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttrAndValue } from 'app/shared/model/attr-and-value.model';

@Component({
  selector: 'jhi-attr-and-value-detail',
  templateUrl: './attr-and-value-detail.component.html'
})
export class AttrAndValueDetailComponent implements OnInit {
  attrAndValue: IAttrAndValue | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attrAndValue }) => (this.attrAndValue = attrAndValue));
  }

  previousState(): void {
    window.history.back();
  }
}
