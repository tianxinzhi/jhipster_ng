export interface IAttrAndValue {
  id?: number;
  attrName?: string;
  attrValue?: string;
}

export class AttrAndValue implements IAttrAndValue {
  constructor(public id?: number, public attrName?: string, public attrValue?: string) {}
}
