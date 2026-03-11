package com.khanabook.saas.repository;

import com.khanabook.saas.entity.RecipeIngredient;
import com.khanabook.saas.sync.repository.SyncRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends SyncRepository<RecipeIngredient, String> {
}
