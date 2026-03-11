package com.khanabook.saas.service;

import com.khanabook.saas.entity.StockLog;
import java.util.List;

public interface StockLogService {
    List<String> pushData(Long tenantId, List<StockLog> payload);
    List<StockLog> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId, Integer limit);
}
