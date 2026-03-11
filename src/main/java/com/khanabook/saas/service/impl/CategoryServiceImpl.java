package com.khanabook.saas.service.impl;

import com.khanabook.saas.entity.Category;
import com.khanabook.saas.repository.CategoryRepository;
import com.khanabook.saas.service.CategoryService;
import com.khanabook.saas.sync.service.GenericSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final GenericSyncService genericSyncService;

    @Override
    public List<String> pushData(Long tenantId, List<Category> payload) {
        return genericSyncService.handlePushSync(tenantId, payload, repository);
    }

    @Override
    public List<Category> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId, Integer limit) {
        return repository.findByRestaurantIdAndServerUpdatedAtGreaterThanAndDeviceIdNotOrderByServerUpdatedAtAsc(tenantId, lastSyncTimestamp, deviceId, PageRequest.of(0, limit));
    }
}
