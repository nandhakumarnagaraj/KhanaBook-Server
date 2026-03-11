package com.khanabook.saas.service.impl;

import com.khanabook.saas.entity.RawMaterial;
import com.khanabook.saas.repository.RawMaterialRepository;
import com.khanabook.saas.service.RawMaterialService;
import com.khanabook.saas.sync.service.GenericSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RawMaterialServiceImpl implements RawMaterialService {
    private final RawMaterialRepository repository;
    private final GenericSyncService genericSyncService;

    @Override
    public List<String> pushData(Long tenantId, List<RawMaterial> payload) {
        return genericSyncService.handlePushSync(tenantId, payload, repository);
    }

    @Override
    public List<RawMaterial> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId, Integer limit) {
        return repository.findByRestaurantIdAndServerUpdatedAtGreaterThanAndDeviceIdNotOrderByServerUpdatedAtAsc(tenantId, lastSyncTimestamp, deviceId, PageRequest.of(0, limit));
    }
}
