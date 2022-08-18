package com.kurly.projectmaic.domain.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

@Getter
@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_at", updatable = false)
    private Long createdAt;

    @Column(name = "modified_at")
    private Long modifiedAt;

    @PrePersist
    public void prePersist() {
        long now = Instant.now().toEpochMilli();

        this.createdAt = now;
        this.modifiedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        long now = Instant.now().toEpochMilli();

        this.modifiedAt = now;
    }
}
