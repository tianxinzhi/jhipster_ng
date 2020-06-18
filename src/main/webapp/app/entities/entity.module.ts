import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'attrAndValue',
        loadChildren: () => import('./attr-and-value/attr-and-value.module').then(m => m.DemoNgAttrAndValueModule)
      },
      {
        path: 'spec',
        loadChildren: () => import('./spec/spec.module').then(m => m.DemoNgSpecModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class DemoNgEntityModule {}
