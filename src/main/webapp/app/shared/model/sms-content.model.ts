import { Moment } from 'moment';

export interface ISmsContent {
    id?: string;
    startAt?: Moment;
    expiredAt?: Moment;
    message?: string;
    productId?: string;
    state?: number;
}

export class SmsContent implements ISmsContent {
    constructor(
        public id?: string,
        public startAt?: Moment,
        public expiredAt?: Moment,
        public message?: string,
        public productId?: string,
        public state?: number
    ) {}
}
