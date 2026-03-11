package com.khanabook.saas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khanabook.saas.entity.Bill;
import com.khanabook.saas.repository.BillItemRepository;
import com.khanabook.saas.repository.BillPaymentRepository;
import com.khanabook.saas.repository.BillRepository;
import com.khanabook.saas.service.BillService;
import com.khanabook.saas.sync.payload.FullBillPushRequest;
import com.khanabook.saas.sync.service.GenericSyncService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final BillItemRepository billItemRepository;
    private final BillPaymentRepository billPaymentRepository;
    private final GenericSyncService genericSyncService;

    @Override
    @Transactional
    public List<String> pushData(Long tenantId, List<Bill> payload) {
        return genericSyncService.handlePushSync(tenantId, payload, billRepository);
    }

    @Override
    @Transactional
    public List<String> pushFullData(Long tenantId, FullBillPushRequest payload) {
        List<String> results = new ArrayList<>();
        long serverTime = System.currentTimeMillis();

        // 1. Handle Deletions (Tombstoning)
        if (payload.getDeletedBillIds() != null && !payload.getDeletedBillIds().isEmpty()) {
            tombstoneRecords(tenantId, payload.getDeletedBillIds(), billRepository, serverTime);
            results.addAll(payload.getDeletedBillIds());
        }
        if (payload.getDeletedItemIds() != null && !payload.getDeletedItemIds().isEmpty()) {
            tombstoneRecords(tenantId, payload.getDeletedItemIds(), billItemRepository, serverTime);
            results.addAll(payload.getDeletedItemIds());
        }
        if (payload.getDeletedPaymentIds() != null && !payload.getDeletedPaymentIds().isEmpty()) {
            tombstoneRecords(tenantId, payload.getDeletedPaymentIds(), billPaymentRepository, serverTime);
            results.addAll(payload.getDeletedPaymentIds());
        }

        // 2. Handle Upserts
        if (payload.getBills() != null)
            results.addAll(genericSyncService.handlePushSync(tenantId, payload.getBills(), billRepository));
        if (payload.getItems() != null)
            results.addAll(genericSyncService.handlePushSync(tenantId, payload.getItems(), billItemRepository));
        if (payload.getPayments() != null)
            results.addAll(genericSyncService.handlePushSync(tenantId, payload.getPayments(), billPaymentRepository));

        // 3. Recalculate Totals for any Bill whose items might have changed
        if (payload.getItems() != null && !payload.getItems().isEmpty()) {
            payload.getItems().stream()
                .map(com.khanabook.saas.entity.BillItem::getServerBillId)
                .distinct()
                .filter(java.util.Objects::nonNull)
                .forEach(serverBillId -> recalculateBillTotal(tenantId, serverBillId));
        }

        return results;
    }

    private <T extends com.khanabook.saas.sync.entity.BaseSyncEntity> void tombstoneRecords(
            Long tenantId, List<String> ids, com.khanabook.saas.sync.repository.SyncRepository<T, String> repo, long serverTime) {
        List<T> existing = repo.findAllById(ids);
        existing.forEach(record -> {
            if (record.getRestaurantId().equals(tenantId)) {
                record.setIsDeleted(true);
                record.setServerUpdatedAt(serverTime);
            }
        });
        repo.saveAll(existing);
    }

    private void recalculateBillTotal(Long tenantId, String billId) {
        billRepository.findById(billId).ifPresent(bill -> {
            if (bill.getRestaurantId().equals(tenantId)) {
                java.math.BigDecimal total = billItemRepository.findByServerBillId(billId).stream()
                    .filter(item -> !item.getIsDeleted())
                    .map(com.khanabook.saas.entity.BillItem::getItemTotal)
                    .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
                bill.setTotalAmount(total);
                bill.setServerUpdatedAt(System.currentTimeMillis());
                billRepository.save(bill);
            }
        });
    }

    @Override
    public List<Bill> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId, Integer limit) {
        return billRepository.findByRestaurantIdAndServerUpdatedAtGreaterThanAndDeviceIdNotOrderByServerUpdatedAtAsc(
            tenantId, lastSyncTimestamp, deviceId, PageRequest.of(0, limit));
    }
}
