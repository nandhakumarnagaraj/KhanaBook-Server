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
    public <T extends BaseSyncEntity> List<Integer> handlePushSync(
            Long tenantId,
            List<T> payload,
            SyncRepository<T, Long> repository) {

        if (payload == null || payload.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> successfulLocalIds = new ArrayList<>();
        List<T> recordsToSave = new ArrayList<>();
        
        // 1. Extract all localIds from the incoming payload
        List<Integer> incomingLocalIds = payload.stream()
                .map(BaseSyncEntity::getLocalId)
                .collect(Collectors.toList());

        // Assuming all records in a single payload come from the same device
        String deviceId = payload.get(0).getDeviceId();
        long serverTime = System.currentTimeMillis();

        // 2. Fetch all existing records from the DB in ONE query
        List<T> existingRecords = repository.findByRestaurantIdAndDeviceIdAndLocalIdIn(
                tenantId, deviceId, incomingLocalIds);

        // 3. Map existing records by localId for fast O(1) lookup
        Map<Integer, T> existingRecordMap = existingRecords.stream()
                .collect(Collectors.toMap(BaseSyncEntity::getLocalId, Function.identity()));

        // 4. Process the payload in memory
        for (T incomingRecord : payload) {
            try {
                incomingRecord.setRestaurantId(tenantId);
                incomingRecord.setServerUpdatedAt(serverTime); // Enforce Server Time

                T existingRecord = existingRecordMap.get(incomingRecord.getLocalId());

                if (existingRecord != null) {
                    // It exists, check if we need to update
                    if (incomingRecord.getUpdatedAt() > existingRecord.getUpdatedAt()) {
                        incomingRecord.setId(existingRecord.getId());
                        recordsToSave.add(incomingRecord);
                        successfulLocalIds.add(incomingRecord.getLocalId());
                    }
                } else {
                    // It doesn't exist, insert new
                    recordsToSave.add(incomingRecord);
                    successfulLocalIds.add(incomingRecord.getLocalId());
                }
            } catch (Exception e) {
                System.err.println("Sync Error for device " + incomingRecord.getDeviceId() + " : " + e.getMessage());
            }
        }

        // 5. Save all records to the DB in a single batch
        if (!recordsToSave.isEmpty()) {
            repository.saveAll(recordsToSave);
        }

        System.out.println("\n[GenericSyncService] Successfully batch synced " + successfulLocalIds.size()
                + " records for Tenant ID: " + tenantId);
        
        return successfulLocalIds;
    }
}
