package com.khanabook.saas.entity;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "menuitems")
@Getter
@Setter
public class MenuItem extends BaseSyncEntity {

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "base_price")
    private Double basePrice;

    @Column(name = "food_type")
    private String foodType;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "low_stock_threshold")
    private Integer lowStockThreshold;

    @Column(name = "created_at")
    private String createdAt;
}
