package com.khanabook.saas.sync.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface SyncRepository<T, ID> extends JpaRepository<T, ID> {
    // PULL Logic: Get newer records for this tenant using server-side timestamp with pagination
    List<T> findByRestaurantIdAndServerUpdatedAtGreaterThanOrderByServerUpdatedAtAsc(Long restaurantId, Long serverUpdatedAt, Pageable pageable);

    // NEW PULL Logic with Echo Check Prevention
    List<T> findByRestaurantIdAndServerUpdatedAtGreaterThanAndDeviceIdNotOrderByServerUpdatedAtAsc(Long restaurantId, Long serverUpdatedAt, String deviceId, Pageable pageable);

    // Legacy non-paginated version (Deprecating)
    List<T> findByRestaurantIdAndServerUpdatedAtGreaterThan(Long restaurantId, Long serverUpdatedAt);

    // OLD Lookups for Mapping
    Optional<T> findByRestaurantIdAndDeviceIdAndLocalId(Long restaurantId, String deviceId, Integer localId);

    // OLD PULL Logic (Deprecating but keeping for transition)
    List<T> findByRestaurantIdAndUpdatedAtGreaterThanAndDeviceIdNot(Long restaurantId, Long lastSyncTimestamp, String deviceId);
}
