package com.khanabook.saas.repository;

import com.khanabook.saas.entity.RawMaterial;
import com.khanabook.saas.sync.repository.SyncRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialRepository extends SyncRepository<RawMaterial, Long> {
}
