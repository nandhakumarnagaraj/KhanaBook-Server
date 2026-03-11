package com.khanabook.saas.controller;

import com.khanabook.saas.entity.Bill;
import com.khanabook.saas.service.BillService;
import com.khanabook.saas.sync.payload.FullBillPushRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.khanabook.saas.security.TenantContext;

@RestController
@RequestMapping("/sync/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService service;

    @PostMapping("/push")
    public ResponseEntity<List<String>> push(@RequestBody List<Bill> payload) {
        return ResponseEntity.ok(service.pushData(TenantContext.getCurrentTenant(), payload));
    }

    @PostMapping("/push/full")
    public ResponseEntity<List<String>> pushFull(@RequestBody FullBillPushRequest payload) {
        return ResponseEntity.ok(service.pushFullData(TenantContext.getCurrentTenant(), payload));
    }

    @GetMapping("/pull")
    public ResponseEntity<List<Bill>> pull(
            @RequestParam Long lastSyncTimestamp,
            @RequestParam String deviceId,
            @RequestParam(required = false, defaultValue = "100") Integer limit) {
        return ResponseEntity.ok(service.pullData(TenantContext.getCurrentTenant(), lastSyncTimestamp, deviceId, limit));
    }
}
