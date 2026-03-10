package com.khanabook.saas.controller;

import com.khanabook.saas.entity.BillItem;
import com.khanabook.saas.service.BillItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.khanabook.saas.security.TenantContext;

@RestController
@RequestMapping("/sync/billitem")
@RequiredArgsConstructor
public class BillItemController {
    private final BillItemService service;

    @PostMapping("/push")
    public ResponseEntity<List<Integer>> push(@RequestBody List<BillItem> payload) {
        // TenantId automatically extracted by JwtFilter
        return ResponseEntity.ok(service.pushData(TenantContext.getCurrentTenant(), payload));
    }

    @GetMapping("/pull")
    public ResponseEntity<List<BillItem>> pull(
            @RequestParam Long lastSyncTimestamp,
            @RequestParam String deviceId) {
        return ResponseEntity.ok(service.pullData(TenantContext.getCurrentTenant(), lastSyncTimestamp, deviceId));
    }
}
