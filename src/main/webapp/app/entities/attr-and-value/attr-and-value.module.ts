import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DemoNgSharedModule } from 'app/shared/shared.module';
import { AttrAndValueComponent } from './attr-and-value.component';
import { AttrAndValueDetailComponent } from './attr-and-value-detail.component';
import { AttrAndValueUpdateComponent } from './attr-and-value-update.component';
import { AttrAndValueDeleteDialogComponent } from './attr-and-value-delete-dialog.component';
import { attrAndValueRoute } from './attr-and-value.route';

@NgModule({
  imports: [DemoNgSharedModule, RouterModule.forChild(attrAndValueRoute)],
  declarations: [AttrAndValueComponent, AttrAndValueDetailComponent, AttrAndValueUpdateComponent, AttrAndValueDeleteDialogComponent],
  entryComponents: [AttrAndValueDeleteDialogComponent]
})
export class DemoNgAttrAndValueModule {}
