package com.ft.domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Subscriber.
 */
@Document(collection = "subscriber")
public class Subscriber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 40)
    @Field("msisdn")
    private String msisdn;

    @NotNull
    @Field("product_id")
    private String productId;

    /**
     * Following complex attributes are here
     * tags List<String>
     * provisioning Map<String, Object>
     * cfg Map<String, Object>
     */
    @ApiModelProperty(value = "Following complex attributes are here tags List<String>provisioning Map<String, Object>cfg Map<String, Object>")
    @Field("trial_cnt")
    private Integer trialCnt;

    @Field("success_cnt")
    private Integer successCnt;

    @NotNull
    @Min(value = -9)
    @Max(value = 9)
    @Field("state")
    private Integer state;

    @Field("join_at")
    private ZonedDateTime joinAt;

    @Field("left_at")
    private ZonedDateTime leftAt;

    @Field("expiry_time")
    private ZonedDateTime expiryTime;

    @Field("charge_last_time")
    private ZonedDateTime chargeLastTime;

    @Field("charge_next_time")
    private ZonedDateTime chargeNextTime;

    @Field("charge_last_success")
    private ZonedDateTime chargeLastSuccess;

    @NotNull
    @Min(value = -9)
    @Max(value = 9)
    @Field("notify")
    private Integer notify;

    @Field("notify_last_time")
    private ZonedDateTime notifyLastTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public Subscriber msisdn(String msisdn) {
        this.msisdn = msisdn;
        return this;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getProductId() {
        return productId;
    }

    public Subscriber productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getTrialCnt() {
        return trialCnt;
    }

    public Subscriber trialCnt(Integer trialCnt) {
        this.trialCnt = trialCnt;
        return this;
    }

    public void setTrialCnt(Integer trialCnt) {
        this.trialCnt = trialCnt;
    }

    public Integer getSuccessCnt() {
        return successCnt;
    }

    public Subscriber successCnt(Integer successCnt) {
        this.successCnt = successCnt;
        return this;
    }

    public void setSuccessCnt(Integer successCnt) {
        this.successCnt = successCnt;
    }

    public Integer getState() {
        return state;
    }

    public Subscriber state(Integer state) {
        this.state = state;
        return this;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public ZonedDateTime getJoinAt() {
        return joinAt;
    }

    public Subscriber joinAt(ZonedDateTime joinAt) {
        this.joinAt = joinAt;
        return this;
    }

    public void setJoinAt(ZonedDateTime joinAt) {
        this.joinAt = joinAt;
    }

    public ZonedDateTime getLeftAt() {
        return leftAt;
    }

    public Subscriber leftAt(ZonedDateTime leftAt) {
        this.leftAt = leftAt;
        return this;
    }

    public void setLeftAt(ZonedDateTime leftAt) {
        this.leftAt = leftAt;
    }

    public ZonedDateTime getExpiryTime() {
        return expiryTime;
    }

    public Subscriber expiryTime(ZonedDateTime expiryTime) {
        this.expiryTime = expiryTime;
        return this;
    }

    public void setExpiryTime(ZonedDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public ZonedDateTime getChargeLastTime() {
        return chargeLastTime;
    }

    public Subscriber chargeLastTime(ZonedDateTime chargeLastTime) {
        this.chargeLastTime = chargeLastTime;
        return this;
    }

    public void setChargeLastTime(ZonedDateTime chargeLastTime) {
        this.chargeLastTime = chargeLastTime;
    }

    public ZonedDateTime getChargeNextTime() {
        return chargeNextTime;
    }

    public Subscriber chargeNextTime(ZonedDateTime chargeNextTime) {
        this.chargeNextTime = chargeNextTime;
        return this;
    }

    public void setChargeNextTime(ZonedDateTime chargeNextTime) {
        this.chargeNextTime = chargeNextTime;
    }

    public ZonedDateTime getChargeLastSuccess() {
        return chargeLastSuccess;
    }

    public Subscriber chargeLastSuccess(ZonedDateTime chargeLastSuccess) {
        this.chargeLastSuccess = chargeLastSuccess;
        return this;
    }

    public void setChargeLastSuccess(ZonedDateTime chargeLastSuccess) {
        this.chargeLastSuccess = chargeLastSuccess;
    }

    public Integer getNotify() {
        return notify;
    }

    public Subscriber notify(Integer notify) {
        this.notify = notify;
        return this;
    }

    public void setNotify(Integer notify) {
        this.notify = notify;
    }

    public ZonedDateTime getNotifyLastTime() {
        return notifyLastTime;
    }

    public Subscriber notifyLastTime(ZonedDateTime notifyLastTime) {
        this.notifyLastTime = notifyLastTime;
        return this;
    }

    public void setNotifyLastTime(ZonedDateTime notifyLastTime) {
        this.notifyLastTime = notifyLastTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subscriber subscriber = (Subscriber) o;
        if (subscriber.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subscriber.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Subscriber{" +
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
