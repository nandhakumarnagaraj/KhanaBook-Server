package com.khanabook.saas.controller;

import com.khanabook.saas.entity.RawMaterialStockLog;
import com.khanabook.saas.service.RawMaterialStockLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.khanabook.saas.security.TenantContext;

@RestController
@RequestMapping("/sync/rawmaterialstocklog")
@RequiredArgsConstructor
public class RawMaterialStockLogController {
    private final RawMaterialStockLogService service;

    @PostMapping("/push")
    public ResponseEntity<List<String>> push(@RequestBody List<RawMaterialStockLog> payload) {
        // TenantId automatically extracted by JwtFilter
        return ResponseEntity.ok(service.pushData(TenantContext.getCurrentTenant(), payload));
    }

    @GetMapping("/pull")
    public ResponseEntity<List<RawMaterialStockLog>> pull(
            @RequestParam Long lastSyncTimestamp,
            @RequestParam String deviceId,
            @RequestParam(required = false, defaultValue = "100") Integer limit) {
        return ResponseEntity.ok(service.pullData(TenantContext.getCurrentTenant(), lastSyncTimestamp, deviceId, limit));
    }
}
