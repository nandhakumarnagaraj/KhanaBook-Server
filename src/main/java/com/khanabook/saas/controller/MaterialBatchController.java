package com.khanabook.saas.controller;

import com.khanabook.saas.entity.MaterialBatch;
import com.khanabook.saas.service.MaterialBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.khanabook.saas.security.TenantContext;

@RestController
@RequestMapping("/api/v1/sync/materialbatch")
@RequiredArgsConstructor
public class MaterialBatchController {
    private final MaterialBatchService service;

    @PostMapping("/push")
    public ResponseEntity<List<Integer>> push(@RequestBody List<MaterialBatch> payload) {
        // TenantId automatically extracted by JwtFilter
        return ResponseEntity.ok(service.pushData(TenantContext.getCurrentTenant(), payload));
    }

    @GetMapping("/pull")
    public ResponseEntity<List<MaterialBatch>> pull(
            @RequestParam Long lastSyncTimestamp,
            @RequestParam String deviceId) {
        return ResponseEntity.ok(service.pullData(TenantContext.getCurrentTenant(), lastSyncTimestamp, deviceId));
    }
}
