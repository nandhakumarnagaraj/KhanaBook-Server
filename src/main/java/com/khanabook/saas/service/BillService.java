package com.khanabook.saas.service;

import com.khanabook.saas.entity.Bill;
import com.khanabook.saas.sync.payload.FullBillPushRequest;
import java.util.List;

public interface BillService {
    List<String> pushData(Long tenantId, List<Bill> payload);
    List<String> pushFullData(Long tenantId, FullBillPushRequest payload);
    List<Bill> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId, Integer limit);
}
