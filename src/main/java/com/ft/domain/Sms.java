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
 * A Sms.
 */
@Document(collection = "sms")
public class Sms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Size(max = 40)
    @Field("source")
    private String source;

    @Size(max = 40)
    @Field("destination")
    private String destination;

    @Field("text")
    private String text;

    @Field("submit_at")
    private ZonedDateTime submitAt;

    @Field("expired_at")
    private ZonedDateTime expiredAt;

    @Field("delivered_at")
    private ZonedDateTime deliveredAt;

    @NotNull
    @Min(value = -9)
    @Max(value = 9)
    @Field("state")
    private Integer state;

    @Field("product_id")
    private String productId;

    @Field("content_id")
    private String contentId;

    /**
     * Following complex attributes are here
     * tags List<String>
     * cfg Map<String, Object>
     * submitRequest Object
     * submitResponse Object
     * deliveryRequest Object
     * deliveryResponse Object
     */
    @ApiModelProperty(value = "Following complex attributes are here tags List<String>cfg Map<String, Object>submitRequest Object submitResponse Object deliveryRequest Object deliveryResponse Object")
    @Field("tnx_id")
    private String tnxId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public Sms source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public Sms destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getText() {
        return text;
    }

    public Sms text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getSubmitAt() {
        return submitAt;
    }

    public Sms submitAt(ZonedDateTime submitAt) {
        this.submitAt = submitAt;
        return this;
    }

    public void setSubmitAt(ZonedDateTime submitAt) {
        this.submitAt = submitAt;
    }

    public ZonedDateTime getExpiredAt() {
        return expiredAt;
    }

    public Sms expiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
        return this;
    }

    public void setExpiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public ZonedDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public Sms deliveredAt(ZonedDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
        return this;
    }

    public void setDeliveredAt(ZonedDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Integer getState() {
        return state;
    }

    public Sms state(Integer state) {
        this.state = state;
        return this;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getProductId() {
        return productId;
    }

    public Sms productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getContentId() {
        return contentId;
    }

    public Sms contentId(String contentId) {
        this.contentId = contentId;
        return this;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getTnxId() {
        return tnxId;
    }

    public Sms tnxId(String tnxId) {
        this.tnxId = tnxId;
        return this;
    }

    public void setTnxId(String tnxId) {
        this.tnxId = tnxId;
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
        Sms sms = (Sms) o;
        if (sms.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sms.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sms{" +
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
