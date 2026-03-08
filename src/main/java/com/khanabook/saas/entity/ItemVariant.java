package com.khanabook.saas.entity;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "itemvariants")
@Getter
@Setter
public class ItemVariant extends BaseSyncEntity {

    @Column(name = "menu_item_id")
    private Integer menuItemId;

    @Column(name = "variant_name")
    private String variantName;

    @Column(name = "price")
    private Double price;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "sort_order")
    private Integer sortOrder;
}
