package com.khanabook.saas.repository;

import com.khanabook.saas.entity.RestaurantProfile;
import com.khanabook.saas.sync.repository.SyncRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantProfileRepository extends SyncRepository<RestaurantProfile, Long> {
}
