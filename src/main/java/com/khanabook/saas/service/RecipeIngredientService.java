package com.khanabook.saas.service;

import com.khanabook.saas.entity.RecipeIngredient;
import java.util.List;

public interface RecipeIngredientService {
    List<Integer> pushData(Long tenantId, List<RecipeIngredient> payload);
    List<RecipeIngredient> pullData(Long tenantId, Long lastSyncTimestamp, String deviceId);
}
