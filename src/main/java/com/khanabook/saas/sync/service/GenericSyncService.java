package com.khanabook.saas.sync.service;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import com.khanabook.saas.sync.repository.SyncRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenericSyncService {

    @Transactional
    public <T extends BaseSyncEntity> List<Integer> handlePushSync(
            Long tenantId,
            List<T> payload,
            SyncRepository<T, Long> repository) {
        List<Integer> successfulLocalIds = new ArrayList<>();

        for (T incomingRecord : payload) {
            try {
                incomingRecord.setRestaurantId(tenantId);
                incomingRecord.setServerUpdatedAt(System.currentTimeMillis()); // Enforce Server Time

                T existingRecord = repository.findByRestaurantIdAndDeviceIdAndLocalId(
                        tenantId,
                        incomingRecord.getDeviceId(),
                        incomingRecord.getLocalId()).orElse(null);

                if (existingRecord != null) {
                    if (incomingRecord.getUpdatedAt() > existingRecord.getUpdatedAt()) {
                        incomingRecord.setId(existingRecord.getId());
                        repository.save(incomingRecord);
                    }
                } else {
                    repository.save(incomingRecord);
                }
                successfulLocalIds.add(incomingRecord.getLocalId());
            } catch (Exception e) {
                System.err.println("Sync Error for device " + incomingRecord.getDeviceId() + " : " + e.getMessage());
            }
        }
        System.out.println("\n[GenericSyncService] Successfully synced " + successfulLocalIds.size()
                + " records for Tenant ID: " + tenantId);
        return successfulLocalIds;
    }
}
