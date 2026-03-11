package com.khanabook.saas.sync.service;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import com.khanabook.saas.sync.repository.SyncRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GenericSyncService {

    @Transactional
    public <T extends BaseSyncEntity> List<String> handlePushSync(
            Long tenantId,
            List<T> payload,
            SyncRepository<T, String> repository) {

        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID (Restaurant ID) cannot be null. Ensure valid JWT is provided.");
        }

        if (payload == null || payload.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> successfulIds = new ArrayList<>();
        List<T> recordsToSave = new ArrayList<>();
        
        List<String> incomingIds = payload.stream()
                .map(BaseSyncEntity::getId)
                .collect(Collectors.toList());

        long serverTime = System.currentTimeMillis();

        // Fetch all existing records by UUID
        List<T> existingRecords = repository.findAllById(incomingIds);

        Map<String, T> existingRecordMap = existingRecords.stream()
                .collect(Collectors.toMap(
                        BaseSyncEntity::getId,
                        Function.identity()
                ));

        for (T incomingRecord : payload) {
            try {
                if (incomingRecord.getId() == null || incomingRecord.getId().isBlank()) {
                    System.err.println("[GenericSyncService] Skipping record with NULL/Empty UUID!");
                    continue;
                }

                incomingRecord.setRestaurantId(tenantId);
                incomingRecord.setServerUpdatedAt(serverTime);

                T existingRecord = existingRecordMap.get(incomingRecord.getId());

                if (existingRecord != null) {
                    // Idempotency Check: If server version is same or newer, skip save but report success
                    if (incomingRecord.getUpdatedAt() <= existingRecord.getUpdatedAt()) {
                        successfulIds.add(incomingRecord.getId());
                        continue; 
                    }
                    // Incoming is newer (collaborative edit or mobile update)
                    recordsToSave.add(incomingRecord);
                    successfulIds.add(incomingRecord.getId());
                } else {
                    // New record
                    recordsToSave.add(incomingRecord);
                    successfulIds.add(incomingRecord.getId());
                }
            } catch (Exception e) {
                System.err.println("Sync Error for record " + incomingRecord.getId() + " : " + e.getMessage());
            }
        }

        if (!recordsToSave.isEmpty()) {
            repository.saveAll(recordsToSave);
        }

        return successfulIds;
    }
}
