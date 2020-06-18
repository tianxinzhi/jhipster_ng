import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAttrAndValue, AttrAndValue } from 'app/shared/model/attr-and-value.model';
import { AttrAndValueService } from './attr-and-value.service';
import { AttrAndValueComponent } from './attr-and-value.component';
import { AttrAndValueDetailComponent } from './attr-and-value-detail.component';
import { AttrAndValueUpdateComponent } from './attr-and-value-update.component';

@Injectable({ providedIn: 'root' })
export class AttrAndValueResolve implements Resolve<IAttrAndValue> {
  constructor(private service: AttrAndValueService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttrAndValue> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attrAndValue: HttpResponse<AttrAndValue>) => {
          if (attrAndValue.body) {
            return of(attrAndValue.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AttrAndValue());
  }
}

export const attrAndValueRoute: Routes = [
  {
    path: '',
    component: AttrAndValueComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demoNgApp.attrAndValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AttrAndValueDetailComponent,
    resolve: {
      attrAndValue: AttrAndValueResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demoNgApp.attrAndValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AttrAndValueUpdateComponent,
    resolve: {
      attrAndValue: AttrAndValueResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demoNgApp.attrAndValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AttrAndValueUpdateComponent,
    resolve: {
      attrAndValue: AttrAndValueResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'demoNgApp.attrAndValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
