export interface ISubProduct {
    id?: string;
    code?: string;
    description?: any;
    misc?: any;
}

export class SubProduct implements ISubProduct {
    constructor(public id?: string, public code?: string, public description?: any, public misc?: any) {}
}
