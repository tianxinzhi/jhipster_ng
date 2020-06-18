export interface IAttrValue {
  id?: number;
  attrName?: string;
  attrValue?: string;
  remark?: string;
}

export class AttrValue implements IAttrValue {
  constructor(public id?: number, public attrName?: string, public attrValue?: string, public remark?: string) {}
}
