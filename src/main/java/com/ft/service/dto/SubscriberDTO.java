package com.ft.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Subscriber entity.
 */
public class SubscriberDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 40)
    private String msisdn;

    @NotNull
    private String productId;

    private Integer trialCnt;

    private Integer successCnt;

    @NotNull
    @Min(value = -9)
    @Max(value = 9)
    private Integer state;

    private ZonedDateTime joinAt;

    private ZonedDateTime leftAt;

    private ZonedDateTime expiryTime;

    private ZonedDateTime chargeLastTime;

    private ZonedDateTime chargeNextTime;

    private ZonedDateTime chargeLastSuccess;

    @NotNull
    @Min(value = -9)
    @Max(value = 9)
    private Integer notify;

    private ZonedDateTime notifyLastTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getTrialCnt() {
        return trialCnt;
    }

    public void setTrialCnt(Integer trialCnt) {
        this.trialCnt = trialCnt;
    }

    public Integer getSuccessCnt() {
        return successCnt;
    }

    public void setSuccessCnt(Integer successCnt) {
        this.successCnt = successCnt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public ZonedDateTime getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(ZonedDateTime joinAt) {
        this.joinAt = joinAt;
    }

    public ZonedDateTime getLeftAt() {
        return leftAt;
    }

    public void setLeftAt(ZonedDateTime leftAt) {
        this.leftAt = leftAt;
    }

    public ZonedDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(ZonedDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public ZonedDateTime getChargeLastTime() {
        return chargeLastTime;
    }

    public void setChargeLastTime(ZonedDateTime chargeLastTime) {
        this.chargeLastTime = chargeLastTime;
    }

    public ZonedDateTime getChargeNextTime() {
        return chargeNextTime;
    }

    public void setChargeNextTime(ZonedDateTime chargeNextTime) {
        this.chargeNextTime = chargeNextTime;
    }

    public ZonedDateTime getChargeLastSuccess() {
        return chargeLastSuccess;
    }

    public void setChargeLastSuccess(ZonedDateTime chargeLastSuccess) {
        this.chargeLastSuccess = chargeLastSuccess;
    }

    public Integer getNotify() {
        return notify;
    }

    public void setNotify(Integer notify) {
        this.notify = notify;
    }

    public ZonedDateTime getNotifyLastTime() {
        return notifyLastTime;
    }

    public void setNotifyLastTime(ZonedDateTime notifyLastTime) {
        this.notifyLastTime = notifyLastTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubscriberDTO subscriberDTO = (SubscriberDTO) o;
        if (subscriberDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subscriberDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubscriberDTO{" +
            "id=" + getId() +
            ", msisdn='" + getMsisdn() + "'" +
            ", productId='" + getProductId() + "'" +
            ", trialCnt=" + getTrialCnt() +
            ", successCnt=" + getSuccessCnt() +
            ", state=" + getState() +
            ", joinAt='" + getJoinAt() + "'" +
            ", leftAt='" + getLeftAt() + "'" +
            ", expiryTime='" + getExpiryTime() + "'" +
            ", chargeLastTime='" + getChargeLastTime() + "'" +
            ", chargeNextTime='" + getChargeNextTime() + "'" +
            ", chargeLastSuccess='" + getChargeLastSuccess() + "'" +
            ", notify=" + getNotify() +
            ", notifyLastTime='" + getNotifyLastTime() + "'" +
            "}";
    }
}
