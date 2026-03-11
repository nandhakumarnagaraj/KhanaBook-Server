package com.khanabook.saas.repository;

import com.khanabook.saas.entity.RawMaterialStockLog;
import com.khanabook.saas.sync.repository.SyncRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialStockLogRepository extends SyncRepository<RawMaterialStockLog, String> {
}
