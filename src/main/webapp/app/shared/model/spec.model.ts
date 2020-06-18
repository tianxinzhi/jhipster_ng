export interface ISpec {
  id?: number;
  specName?: string;
  specDesc?: string;
}

export class Spec implements ISpec {
  constructor(public id?: number, public specName?: string, public specDesc?: string) {}
}
