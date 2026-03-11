package com.khanabook.saas.entity;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "raw_material_stock_logs")
@Getter
@Setter
public class RawMaterialStockLog extends BaseSyncEntity {

    @Column(name = "raw_material_id")
    private Integer rawMaterialId;

    @Column(name = "server_raw_material_id")
    private String serverRawMaterialId;

    @Column(name = "delta")
    private Double delta;

    @Column(name = "reason")
    private String reason; // 'purchase', 'usage', 'wastage', 'initial'

    @Column(name = "created_at")
    private String createdAt;
}
