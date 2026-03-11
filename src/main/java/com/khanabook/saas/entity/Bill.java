package com.khanabook.saas.entity;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "bills")
@Getter
@Setter
public class Bill extends BaseSyncEntity {

    @Column(name = "daily_order_id")
    private Integer dailyOrderId;

    @Column(name = "daily_order_display")
    private String dailyOrderDisplay;

    @Column(name = "lifetime_order_id")
    private Integer lifetimeOrderId;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_whatsapp")
    private String customerWhatsapp;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "gst_percentage")
    private BigDecimal gstPercentage;

    @Column(name = "cgst_amount")
    private BigDecimal cgstAmount;

    @Column(name = "sgst_amount")
    private BigDecimal sgstAmount;

    @Column(name = "custom_tax_amount")
    private BigDecimal customTaxAmount;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "part_amount_1")
    private BigDecimal partAmount1;

    @Column(name = "part_amount_2")
    private BigDecimal partAmount2;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "paid_at")
    private Long paidAt;
}
