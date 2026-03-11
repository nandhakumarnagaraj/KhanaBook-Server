package com.khanabook.saas.entity;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "bill_items")
@Getter
@Setter
public class BillItem extends BaseSyncEntity {

    @Column(name = "bill_id", nullable = true)
    private Integer billId;

    @Column(name = "server_bill_id", nullable = true)
    private String serverBillId;

    @Column(name = "menu_item_id", nullable = true)
    private Integer menuItemId;

    @Column(name = "server_menu_item_id", nullable = true)
    private String serverMenuItemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "variant_id", nullable = true)
    private Integer variantId;

    @Column(name = "server_variant_id", nullable = true)
    private String serverVariantId;

    @Column(name = "variant_name")
    private String variantName;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "item_total")
    private BigDecimal itemTotal;

    @Column(name = "special_instruction")
    private String specialInstruction;
}
