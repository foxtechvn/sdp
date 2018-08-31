import { Moment } from 'moment';

export interface IDnd {
    id?: string;
    joinAt?: Moment;
    joinChannel?: string;
}

export class Dnd implements IDnd {
    constructor(public id?: string, public joinAt?: Moment, public joinChannel?: string) {}
}
