import { Moment } from 'moment';

export interface ICdr {
    id?: string;
    msisdn?: string;
    state?: number;
    amount?: number;
    requestAt?: Moment;
    responseAt?: Moment;
    productId?: string;
    contentId?: string;
}

export class Cdr implements ICdr {
    constructor(
        public id?: string,
        public msisdn?: string,
        public state?: number,
        public amount?: number,
        public requestAt?: Moment,
        public responseAt?: Moment,
        public productId?: string,
        public contentId?: string
    ) {}
}
