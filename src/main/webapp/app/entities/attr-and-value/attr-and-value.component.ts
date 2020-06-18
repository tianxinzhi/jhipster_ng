import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttrAndValue } from 'app/shared/model/attr-and-value.model';
import { AttrAndValueService } from './attr-and-value.service';
import { AttrAndValueDeleteDialogComponent } from './attr-and-value-delete-dialog.component';

@Component({
  selector: 'jhi-attr-and-value',
  templateUrl: './attr-and-value.component.html'
})
export class AttrAndValueComponent implements OnInit, OnDestroy {
  attrAndValues?: IAttrAndValue[];
  eventSubscriber?: Subscription;

  constructor(
    protected attrAndValueService: AttrAndValueService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.attrAndValueService.query().subscribe((res: HttpResponse<IAttrAndValue[]>) => (this.attrAndValues = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAttrAndValues();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttrAndValue): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAttrAndValues(): void {
    this.eventSubscriber = this.eventManager.subscribe('attrAndValueListModification', () => this.loadAll());
  }

  delete(attrAndValue: IAttrAndValue): void {
    const modalRef = this.modalService.open(AttrAndValueDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.attrAndValue = attrAndValue;
  }
}
