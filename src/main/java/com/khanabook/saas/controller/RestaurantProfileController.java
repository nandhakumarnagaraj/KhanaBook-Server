package com.khanabook.saas.controller;

import com.khanabook.saas.entity.RestaurantProfile;
import com.khanabook.saas.service.RestaurantProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.khanabook.saas.security.TenantContext;

@RestController
@RequestMapping("/sync/restaurantprofile")
@RequiredArgsConstructor
public class RestaurantProfileController {
    private final RestaurantProfileService service;

    @PostMapping("/push")
    public ResponseEntity<List<String>> push(@RequestBody List<RestaurantProfile> payload) {
        // TenantId automatically extracted by JwtFilter
        return ResponseEntity.ok(service.pushData(TenantContext.getCurrentTenant(), payload));
    }

    @GetMapping("/pull")
    public ResponseEntity<List<RestaurantProfile>> pull(
            @RequestParam Long lastSyncTimestamp,
            @RequestParam String deviceId,
            @RequestParam(required = false, defaultValue = "100") Integer limit) {
        return ResponseEntity.ok(service.pullData(TenantContext.getCurrentTenant(), lastSyncTimestamp, deviceId, limit));
    }
}
