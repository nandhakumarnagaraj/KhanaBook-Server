package com.khanabook.saas.sync.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseSyncEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    @JsonProperty("id")
    private String id; // Globally Unique Identifier (UUID)

    @Column(name = "device_id", nullable = false)
    @JsonProperty("deviceId")
    private String deviceId;

    @Column(name = "local_id", nullable = false)
    @JsonProperty("localId")
    private Integer localId; // Mobile-local ID

    @Column(name = "restaurant_id", nullable = false)
    @JsonProperty("restaurantId")
    private Long restaurantId; // TENANT ID

    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updatedAt")
    private Long updatedAt; // App's local timestamp

    // FIX 1 & 3: Soft Delete and Clock Skew Protection
    @Column(name = "is_deleted", nullable = false)
    @JsonProperty("isDeleted")
    private Boolean isDeleted = false;

    @Column(name = "server_updated_at", nullable = false)
    @JsonProperty("serverUpdatedAt")
    private Long serverUpdatedAt = System.currentTimeMillis();

    @Version
    @JsonProperty("version")
    private Integer version;
}
