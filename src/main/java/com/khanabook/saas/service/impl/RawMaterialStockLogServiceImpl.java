package com.khanabook.saas.service.impl;

import com.khanabook.saas.entity.RawMaterialStockLog;
import com.khanabook.saas.repository.RawMaterialStockLogRepository;
import com.khanabook.saas.service.RawMaterialStockLogService;
import com.khanabook.saas.sync.service.GenericSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RawMaterialStockLogServiceImpl implements RawMaterialStockLogService {
    private final RawMaterialStockLogRepository repository;
    private final GenericSyncService genericSyncService;

    @Override
    public List<Integer> pushData(Long tenantId, List<RawMaterialStockLog> payload) {
        return genericSyncService.handlePushSync(tenantId, payload, repository);
    }

    @Override
    public List<RawMaterialStockLog> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId) {
        return repository.findByRestaurantIdAndUpdatedAtGreaterThanAndDeviceIdNot(tenantId, lastSyncTimestamp, deviceId);
    }
}
