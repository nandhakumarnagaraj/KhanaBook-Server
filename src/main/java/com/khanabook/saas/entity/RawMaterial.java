package com.khanabook.saas.entity;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rawmaterials")
@Getter
@Setter
public class RawMaterial extends BaseSyncEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "unit")
    private String unit;

    @Column(name = "current_stock")
    private Double currentStock;

    @Column(name = "low_stock_threshold")
    private Double lowStockThreshold;

    @Column(name = "last_updated")
    private String lastUpdated;
}
