package com.khanabook.saas.entity;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stock_logs")
@Getter
@Setter
public class StockLog extends BaseSyncEntity {

    @Column(name = "menu_item_id")
    private Integer menuItemId;

    @Column(name = "server_menu_item_id")
    private Long serverMenuItemId;

    @Column(name = "delta")
    private Integer delta;

    @Column(name = "reason")
    private String reason; // 'sale', 'adjustment', 'initial'

    @Column(name = "created_at")
    private String createdAt;
}
