package com.ft.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Cdr entity.
 */
public class CdrDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 40)
    private String msisdn;

    @Min(value = -9)
    @Max(value = 9)
    private Integer state;

    @NotNull
    private Double amount;

    private ZonedDateTime requestAt;

    private ZonedDateTime responseAt;

    private String productId;

    private String contentId;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ZonedDateTime getRequestAt() {
        return requestAt;
    }

    public void setRequestAt(ZonedDateTime requestAt) {
        this.requestAt = requestAt;
    }

    public ZonedDateTime getResponseAt() {
        return responseAt;
    }

    public void setResponseAt(ZonedDateTime responseAt) {
        this.responseAt = responseAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CdrDTO cdrDTO = (CdrDTO) o;
        if (cdrDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cdrDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CdrDTO{" +
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
