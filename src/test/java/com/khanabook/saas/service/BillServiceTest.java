package com.khanabook.saas.service;

import com.khanabook.saas.entity.Bill;
import com.khanabook.saas.repository.BillRepository;
import com.khanabook.saas.service.impl.BillServiceImpl;
import com.khanabook.saas.sync.service.GenericSyncService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    @Mock
    private BillRepository billRepository;

    @Spy
    private GenericSyncService genericSyncService; // Use real sync logic but can mock repository

    @InjectMocks
    private BillServiceImpl billService;

    @Captor
    private ArgumentCaptor<List<Bill>> billsCaptor;

    private final Long AUTHENTICATED_RESTAURANT_ID = 99L;
    private final String DEVICE_ID = "TABLET_1";

    private Bill createMobileBill(String id, Integer localId, Long updatedAt) {
        Bill bill = new Bill();
        bill.setId(id);
        bill.setLocalId(localId);
        bill.setUpdatedAt(updatedAt);
        bill.setDeviceId(DEVICE_ID);
        return bill;
    }

    @Test
    void givenExistingBill_whenMobileIsNewer_thenUpdateLwwSuccess() {
        Long oldServerTime = 1000L;
        Long newMobileTime = 2000L;
        String billId = "bill-uuid-123";

        Bill existingDbBill = new Bill();
        existingDbBill.setId(billId);
        existingDbBill.setUpdatedAt(oldServerTime);
        existingDbBill.setDeviceId(DEVICE_ID);
        existingDbBill.setLocalId(101);

        Bill mobileBill = createMobileBill(billId, 101, newMobileTime);

        when(billRepository.findAllById(List.of(billId)))
                .thenReturn(List.of(existingDbBill));

        List<String> successIds = billService.pushData(AUTHENTICATED_RESTAURANT_ID, List.of(mobileBill));

        verify(billRepository).saveAll(billsCaptor.capture());
        Bill savedBill = billsCaptor.getValue().get(0);

        assertThat(savedBill.getId()).isEqualTo(billId);
        assertThat(savedBill.getUpdatedAt()).isEqualTo(newMobileTime);
        assertThat(successIds).containsExactly(billId);
    }

    @Test
    void givenHackedPayload_whenInsertNewBill_thenForceTenantIsolation() {
        Long maliciousRestaurantId = 666L;
        String billId = "new-bill-uuid";
        Bill hackedMobileBill = createMobileBill(billId, 202, 1000L);
        hackedMobileBill.setRestaurantId(maliciousRestaurantId);

        when(billRepository.findAllById(anyList()))
                .thenReturn(List.of());

        billService.pushData(AUTHENTICATED_RESTAURANT_ID, List.of(hackedMobileBill));

        verify(billRepository).saveAll(billsCaptor.capture());
        Bill savedBill = billsCaptor.getValue().get(0);

        // Server MUST override the malicious ID with the authenticated one
        assertThat(savedBill.getRestaurantId()).isEqualTo(AUTHENTICATED_RESTAURANT_ID);
    }
}
