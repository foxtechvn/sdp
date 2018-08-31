import { Moment } from 'moment';

export interface ISubscriber {
    id?: string;
    msisdn?: string;
    productId?: string;
    trialCnt?: number;
    successCnt?: number;
    state?: number;
    joinAt?: Moment;
    leftAt?: Moment;
    expiryTime?: Moment;
    chargeLastTime?: Moment;
    chargeNextTime?: Moment;
    chargeLastSuccess?: Moment;
    notify?: number;
    notifyLastTime?: Moment;
}

export class Subscriber implements ISubscriber {
    constructor(
        public id?: string,
        public msisdn?: string,
        public productId?: string,
        public trialCnt?: number,
        public successCnt?: number,
        public state?: number,
        public joinAt?: Moment,
        public leftAt?: Moment,
        public expiryTime?: Moment,
        public chargeLastTime?: Moment,
        public chargeNextTime?: Moment,
        public chargeLastSuccess?: Moment,
        public notify?: number,
        public notifyLastTime?: Moment
    ) {}
}
