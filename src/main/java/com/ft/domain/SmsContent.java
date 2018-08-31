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
 * A SmsContent.
 */
@Document(collection = "sms_content")
public class SmsContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("start_at")
    private ZonedDateTime startAt;

    @Field("expired_at")
    private ZonedDateTime expiredAt;

    @Field("message")
    private String message;

    @Field("product_id")
    private String productId;

    /**
     * Following complex attributes are here
     * tags List<String>
     * cfg Map<String, Object>
     */
    @NotNull
    @Min(value = -9)
    @Max(value = 9)
    @ApiModelProperty(value = "Following complex attributes are here tags List<String>cfg Map<String, Object>", required = true)
    @Field("state")
    private Integer state;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getStartAt() {
        return startAt;
    }

    public SmsContent startAt(ZonedDateTime startAt) {
        this.startAt = startAt;
        return this;
    }

    public void setStartAt(ZonedDateTime startAt) {
        this.startAt = startAt;
    }

    public ZonedDateTime getExpiredAt() {
        return expiredAt;
    }

    public SmsContent expiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
        return this;
    }

    public void setExpiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getMessage() {
        return message;
    }

    public SmsContent message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProductId() {
        return productId;
    }

    public SmsContent productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getState() {
        return state;
    }

    public SmsContent state(Integer state) {
        this.state = state;
        return this;
    }

    public void setState(Integer state) {
        this.state = state;
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
        SmsContent smsContent = (SmsContent) o;
        if (smsContent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), smsContent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SmsContent{" +
            "id=" + getId() +
            ", startAt='" + getStartAt() + "'" +
            ", expiredAt='" + getExpiredAt() + "'" +
            ", message='" + getMessage() + "'" +
            ", productId='" + getProductId() + "'" +
            ", state=" + getState() +
            "}";
    }
}
