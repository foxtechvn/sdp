package com.ft.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Dnd entity.
 */
public class DndDTO implements Serializable {

    private String id;

    private ZonedDateTime joinAt;

    @Size(max = 40)
    private String joinChannel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(ZonedDateTime joinAt) {
        this.joinAt = joinAt;
    }

    public String getJoinChannel() {
        return joinChannel;
    }

    public void setJoinChannel(String joinChannel) {
        this.joinChannel = joinChannel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DndDTO dndDTO = (DndDTO) o;
        if (dndDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dndDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DndDTO{" +
            "id=" + getId() +
            ", joinAt='" + getJoinAt() + "'" +
            ", joinChannel='" + getJoinChannel() + "'" +
            "}";
    }
}
