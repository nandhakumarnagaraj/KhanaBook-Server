package com.khanabook.saas.entity;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
    private BigDecimal currentStock;

    @Column(name = "low_stock_threshold")
    private BigDecimal lowStockThreshold;

    @Column(name = "last_updated")
    private String lastUpdated;
}
