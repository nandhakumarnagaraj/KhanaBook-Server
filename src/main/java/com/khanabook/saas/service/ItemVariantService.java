package com.khanabook.saas.service;

import com.khanabook.saas.entity.ItemVariant;
import java.util.List;

public interface ItemVariantService {
    List<String> pushData(Long tenantId, List<ItemVariant> payload);
    List<ItemVariant> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId, Integer limit);
}
