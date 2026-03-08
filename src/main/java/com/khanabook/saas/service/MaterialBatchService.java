package com.khanabook.saas.service;

import com.khanabook.saas.entity.MaterialBatch;
import java.util.List;

public interface MaterialBatchService {
    List<Integer> pushData(Long tenantId, List<MaterialBatch> payload);
    List<MaterialBatch> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId);
}
