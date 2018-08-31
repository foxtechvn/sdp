import { Moment } from 'moment';

export interface ISms {
    id?: string;
    source?: string;
    destination?: string;
    text?: string;
    submitAt?: Moment;
    expiredAt?: Moment;
    deliveredAt?: Moment;
    state?: number;
    productId?: string;
    contentId?: string;
    tnxId?: string;
}

export class Sms implements ISms {
    constructor(
        public id?: string,
        public source?: string,
        public destination?: string,
        public text?: string,
        public submitAt?: Moment,
        public expiredAt?: Moment,
        public deliveredAt?: Moment,
        public state?: number,
        public productId?: string,
        public contentId?: string,
        public tnxId?: string
    ) {}
}
