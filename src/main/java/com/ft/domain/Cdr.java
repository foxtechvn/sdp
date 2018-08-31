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
 * A Cdr.
 */
@Document(collection = "cdr")
public class Cdr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 40)
    @Field("msisdn")
    private String msisdn;

    @Min(value = -9)
    @Max(value = 9)
    @Field("state")
    private Integer state;

    @NotNull
    @Field("amount")
    private Double amount;

    @Field("request_at")
    private ZonedDateTime requestAt;

    @Field("response_at")
    private ZonedDateTime responseAt;

    /**
     * Following complex attributes are here
     * tags List<String>
     * cfg Map<String, Object>
     * chargeRequest Object
     * chargeResponse Object
     */
    @ApiModelProperty(value = "Following complex attributes are here tags List<String>cfg Map<String, Object>chargeRequest Object chargeResponse Object")
    @Field("product_id")
    private String productId;

    @Field("content_id")
    private String contentId;

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

    public Cdr msisdn(String msisdn) {
        this.msisdn = msisdn;
        return this;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getState() {
        return state;
    }

    public Cdr state(Integer state) {
        this.state = state;
        return this;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getAmount() {
        return amount;
    }

    public Cdr amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ZonedDateTime getRequestAt() {
        return requestAt;
    }

    public Cdr requestAt(ZonedDateTime requestAt) {
        this.requestAt = requestAt;
        return this;
    }

    public void setRequestAt(ZonedDateTime requestAt) {
        this.requestAt = requestAt;
    }

    public ZonedDateTime getResponseAt() {
        return responseAt;
    }

    public Cdr responseAt(ZonedDateTime responseAt) {
        this.responseAt = responseAt;
        return this;
    }

    public void setResponseAt(ZonedDateTime responseAt) {
        this.responseAt = responseAt;
    }

    public String getProductId() {
        return productId;
    }

    public Cdr productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getContentId() {
        return contentId;
    }

    public Cdr contentId(String contentId) {
        this.contentId = contentId;
        return this;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
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
        Cdr cdr = (Cdr) o;
        if (cdr.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cdr.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cdr{" +
            "id=" + getId() +
            ", msisdn='" + getMsisdn() + "'" +
            ", state=" + getState() +
            ", amount=" + getAmount() +
            ", requestAt='" + getRequestAt() + "'" +
            ", responseAt='" + getResponseAt() + "'" +
            ", productId='" + getProductId() + "'" +
            ", contentId='" + getContentId() + "'" +
            "}";
    }
}
