package com.khanabook.saas.entity;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "material_batches")
@Getter
@Setter
public class MaterialBatch extends BaseSyncEntity {

    @Column(name = "raw_material_id")
    private Integer rawMaterialId;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "initial_quantity")
    private Double initialQuantity;

    @Column(name = "expiry_date")
    private String expiryDate; // yyyy-MM-dd

    @Column(name = "received_date")
    private String receivedDate;

    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "is_depleted")
    private Boolean isDepleted;
}
