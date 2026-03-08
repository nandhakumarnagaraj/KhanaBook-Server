package com.khanabook.saas.service;

import com.khanabook.saas.entity.RawMaterialStockLog;
import java.util.List;

public interface RawMaterialStockLogService {
    List<Integer> pushData(Long tenantId, List<RawMaterialStockLog> payload);
    List<RawMaterialStockLog> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId);
}
