import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttrAndValue } from 'app/shared/model/attr-and-value.model';

type EntityResponseType = HttpResponse<IAttrAndValue>;
type EntityArrayResponseType = HttpResponse<IAttrAndValue[]>;

@Injectable({ providedIn: 'root' })
export class AttrAndValueService {
  public resourceUrl = SERVER_API_URL + 'api/attr-and-values';

  constructor(protected http: HttpClient) {}

  create(attrAndValue: IAttrAndValue): Observable<EntityResponseType> {
    return this.http.post<IAttrAndValue>(this.resourceUrl, attrAndValue, { observe: 'response' });
  }

  update(attrAndValue: IAttrAndValue): Observable<EntityResponseType> {
    return this.http.put<IAttrAndValue>(this.resourceUrl, attrAndValue, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttrAndValue>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttrAndValue[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
