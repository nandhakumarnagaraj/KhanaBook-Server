package com.khanabook.saas.entity;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bill_items")
@Getter
@Setter
public class BillItem extends BaseSyncEntity {

    @Column(name = "bill_id")
    private Integer billId;

    @Column(name = "menu_item_id")
    private Integer menuItemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "variant_id")
    private Integer variantId;

    @Column(name = "variant_name")
    private String variantName;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "item_total")
    private Double itemTotal;

    @Column(name = "special_instruction")
    private String specialInstruction;
}
