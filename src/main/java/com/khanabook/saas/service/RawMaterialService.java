package com.khanabook.saas.service;

import com.khanabook.saas.entity.RawMaterial;
import java.util.List;

public interface RawMaterialService {
    List<Integer> pushData(Long tenantId, List<RawMaterial> payload);
    List<RawMaterial> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId);
}
