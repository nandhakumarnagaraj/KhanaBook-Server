package com.khanabook.saas.sync.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseSyncEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "local_id", nullable = false)
    private Integer localId;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId; // TENANT ID

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt; // App's local timestamp

    // FIX 1 & 3: Soft Delete and Clock Skew Protection
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "server_updated_at", nullable = false)
    private Long serverUpdatedAt = System.currentTimeMillis();
}
