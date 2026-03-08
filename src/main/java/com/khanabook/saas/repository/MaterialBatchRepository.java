package com.khanabook.saas.repository;

import com.khanabook.saas.entity.MaterialBatch;
import com.khanabook.saas.sync.repository.SyncRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialBatchRepository extends SyncRepository<MaterialBatch, Long> {
}
