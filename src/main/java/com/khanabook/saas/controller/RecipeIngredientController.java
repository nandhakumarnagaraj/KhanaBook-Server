package com.khanabook.saas.controller;

import com.khanabook.saas.entity.RecipeIngredient;
import com.khanabook.saas.service.RecipeIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.khanabook.saas.security.TenantContext;

@RestController
@RequestMapping("/sync/recipeingredient")
@RequiredArgsConstructor
public class RecipeIngredientController {
    private final RecipeIngredientService service;

    @PostMapping("/push")
    public ResponseEntity<List<String>> push(@RequestBody List<RecipeIngredient> payload) {
        // TenantId automatically extracted by JwtFilter
        return ResponseEntity.ok(service.pushData(TenantContext.getCurrentTenant(), payload));
    }

    @GetMapping("/pull")
    public ResponseEntity<List<RecipeIngredient>> pull(
            @RequestParam Long lastSyncTimestamp,
            @RequestParam String deviceId,
            @RequestParam(required = false, defaultValue = "100") Integer limit) {
        return ResponseEntity.ok(service.pullData(TenantContext.getCurrentTenant(), lastSyncTimestamp, deviceId, limit));
    }
}
