import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttrAndValue } from 'app/shared/model/attr-and-value.model';
import { AttrAndValueService } from './attr-and-value.service';

@Component({
  templateUrl: './attr-and-value-delete-dialog.component.html'
})
export class AttrAndValueDeleteDialogComponent {
  attrAndValue?: IAttrAndValue;

  constructor(
    protected attrAndValueService: AttrAndValueService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attrAndValueService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attrAndValueListModification');
      this.activeModal.close();
    });
  }
}
