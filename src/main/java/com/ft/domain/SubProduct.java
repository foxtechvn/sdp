package com.ft.domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SubProduct.
 */
@Document(collection = "sub_product")
public class SubProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * The name of the product
     */
    @NotNull
    @Size(max = 80)
    @ApiModelProperty(value = "The name of the product", required = true)
    @Field("code")
    private String code;

    /**
     * Description
     */
    @ApiModelProperty(value = "Description")
    @Field("description")
    private String description;

    /**
     * Following complex attributes are here
     * chargingProfiles Map<String, Object>: Charging Profile
     * joinPattern List<String>
     * leftPattern List<String>
     * tags List<String>
     * broadcast Map<String, Object>: Broadcast Information
     * msg Map<String, String>: Message types: contents
     */
    @ApiModelProperty(value = "Following complex attributes are here chargingProfiles Map<String, Object>: Charging Profile joinPattern List<String>leftPattern List<String>tags List<String>broadcast Map<String, Object>: Broadcast Information msg Map<String, String>: Message types: contents")
    @Field("misc")
    private String misc;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public SubProduct code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public SubProduct description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMisc() {
        return misc;
    }

    public SubProduct misc(String misc) {
        this.misc = misc;
        return this;
    }

    public void setMisc(String misc) {
        this.misc = misc;
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
        SubProduct subProduct = (SubProduct) o;
        if (subProduct.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subProduct.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubProduct{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", misc='" + getMisc() + "'" +
            "}";
    }
}
