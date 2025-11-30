package com.graduation.backend.repository;

import com.graduation.backend.entity.BranchStock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BranchStockRepository extends JpaRepository<BranchStock,Long> {
    Optional<BranchStock> findByBranchIdAndProductId(Long branchId,Long productId);
    List<BranchStock> findAllByBranchId(Long branchId);

}
