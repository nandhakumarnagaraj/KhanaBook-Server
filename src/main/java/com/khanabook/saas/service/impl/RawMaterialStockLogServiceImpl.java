package com.khanabook.saas.service.impl;

import com.khanabook.saas.entity.RawMaterial;
import com.khanabook.saas.entity.RawMaterialStockLog;
import com.khanabook.saas.repository.RawMaterialRepository;
import com.khanabook.saas.repository.RawMaterialStockLogRepository;
import com.khanabook.saas.service.RawMaterialStockLogService;
import com.khanabook.saas.sync.service.GenericSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RawMaterialStockLogServiceImpl implements RawMaterialStockLogService {
    private final RawMaterialStockLogRepository repository;
    private final RawMaterialRepository rawMaterialRepository;
    private final GenericSyncService genericSyncService;

    @Override
    public List<Integer> pushData(Long tenantId, List<RawMaterialStockLog> payload) {
        // Resolve Server IDs for raw materials
        for (RawMaterialStockLog log : payload) {
            if (log.getServerRawMaterialId() == null && log.getRawMaterialId() != null) {
                Optional<RawMaterial> rm = rawMaterialRepository.findByRestaurantIdAndDeviceIdAndLocalId(
                        tenantId, log.getDeviceId(), log.getRawMaterialId());
                rm.ifPresent(item -> log.setServerRawMaterialId(item.getId()));
                
                if (log.getServerRawMaterialId() == null) {
                    log.setServerRawMaterialId(0L); // Placeholder
                }
            }
        }
        return genericSyncService.handlePushSync(tenantId, payload, repository);
    }

    @Override
    public List<RawMaterialStockLog> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId) {
        return repository.findByRestaurantIdAndUpdatedAtGreaterThanAndDeviceIdNot(tenantId, lastSyncTimestamp, deviceId);
    }
}
