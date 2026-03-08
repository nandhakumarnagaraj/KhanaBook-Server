package com.khanabook.saas.entity;

import com.khanabook.saas.sync.entity.BaseSyncEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "itemvariants")
@Getter
@Setter
public class ItemVariant extends BaseSyncEntity {
    // Fields matching Android model
}
