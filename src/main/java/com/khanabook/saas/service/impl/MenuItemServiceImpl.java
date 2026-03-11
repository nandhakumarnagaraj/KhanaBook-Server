package com.khanabook.saas.service.impl;

import com.khanabook.saas.entity.MenuItem;
import com.khanabook.saas.repository.MenuItemRepository;
import com.khanabook.saas.service.MenuItemService;
import com.khanabook.saas.sync.service.GenericSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repository;
    private final GenericSyncService genericSyncService;

    @Override
    public List<String> pushData(Long tenantId, List<MenuItem> payload) {
        return genericSyncService.handlePushSync(tenantId, payload, repository);
    }

    @Override
    public List<MenuItem> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId, Integer limit) {
        return repository.findByRestaurantIdAndServerUpdatedAtGreaterThanAndDeviceIdNotOrderByServerUpdatedAtAsc(tenantId, lastSyncTimestamp, deviceId, PageRequest.of(0, limit));
    }
}
