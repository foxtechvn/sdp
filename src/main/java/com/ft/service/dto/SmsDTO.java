package com.ft.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Sms entity.
 */
public class SmsDTO implements Serializable {

    private String id;

    @Size(max = 40)
    private String source;

    @Size(max = 40)
    private String destination;

    private String text;

    private ZonedDateTime submitAt;

    private ZonedDateTime expiredAt;

    private ZonedDateTime deliveredAt;

    @NotNull
    @Min(value = -9)
    @Max(value = 9)
    private Integer state;

    private String productId;

    private String contentId;

    private String tnxId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getSubmitAt() {
        return submitAt;
    }

    public void setSubmitAt(ZonedDateTime submitAt) {
        this.submitAt = submitAt;
    }

    public ZonedDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public ZonedDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(ZonedDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getTnxId() {
        return tnxId;
    }

    public void setTnxId(String tnxId) {
        this.tnxId = tnxId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SmsDTO smsDTO = (SmsDTO) o;
        if (smsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), smsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SmsDTO{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", destination='" + getDestination() + "'" +
            ", text='" + getText() + "'" +
            ", submitAt='" + getSubmitAt() + "'" +
            ", expiredAt='" + getExpiredAt() + "'" +
            ", deliveredAt='" + getDeliveredAt() + "'" +
            ", state=" + getState() +
            ", productId='" + getProductId() + "'" +
            ", contentId='" + getContentId() + "'" +
            ", tnxId='" + getTnxId() + "'" +
            "}";
    }
}
