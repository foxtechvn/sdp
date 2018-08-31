package com.ft.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SmsContent entity.
 */
public class SmsContentDTO implements Serializable {

    private String id;

    private ZonedDateTime startAt;

    private ZonedDateTime expiredAt;

    private String message;

    private String productId;

    @NotNull
    @Min(value = -9)
    @Max(value = 9)
    private Integer state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(ZonedDateTime startAt) {
        this.startAt = startAt;
    }

    public ZonedDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SmsContentDTO smsContentDTO = (SmsContentDTO) o;
        if (smsContentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), smsContentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SmsContentDTO{" +
            "id=" + getId() +
            ", startAt='" + getStartAt() + "'" +
            ", expiredAt='" + getExpiredAt() + "'" +
            ", message='" + getMessage() + "'" +
            ", productId='" + getProductId() + "'" +
            ", state=" + getState() +
            "}";
    }
}
