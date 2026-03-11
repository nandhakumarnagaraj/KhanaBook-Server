package com.khanabook.saas.service;

import com.khanabook.saas.entity.RestaurantProfile;
import java.util.List;

public interface RestaurantProfileService {
    List<String> pushData(Long tenantId, List<RestaurantProfile> payload);
    List<RestaurantProfile> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId, Integer limit);
}
