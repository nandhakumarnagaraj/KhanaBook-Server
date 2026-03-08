package com.khanabook.saas.controller;

import com.khanabook.saas.entity.Category;
import com.khanabook.saas.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.khanabook.saas.security.TenantContext;

@RestController
@RequestMapping("/api/v1/sync/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @PostMapping("/push")
    public ResponseEntity<List<Integer>> push(@RequestBody List<Category> payload) {
        // TenantId automatically extracted by JwtFilter
        return ResponseEntity.ok(service.pushData(TenantContext.getCurrentTenant(), payload));
    }

    @GetMapping("/pull")
    public ResponseEntity<List<Category>> pull(
            @RequestParam Long lastSyncTimestamp,
            @RequestParam String deviceId) {
        return ResponseEntity.ok(service.pullData(TenantContext.getCurrentTenant(), lastSyncTimestamp, deviceId));
    }
}
