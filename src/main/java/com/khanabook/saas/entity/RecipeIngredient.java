package com.khanabook.saas.entity;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recipe_ingredients")
@Getter
@Setter
public class RecipeIngredient extends BaseSyncEntity {

    @Column(name = "menu_item_id")
    private Integer menuItemId;

    @Column(name = "raw_material_id")
    private Integer rawMaterialId;

    @Column(name = "quantity_needed")
    private Double quantityNeeded;
}
