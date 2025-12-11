package com.graduation.backend.repository;

import com.graduation.backend.entity.BranchSale;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface BranchSaleRepository extends JpaRepository<BranchSale, Long> {

    List<BranchSale> findAllByBranchId (Long branchId);
    List<BranchSale> findAllByBranchIdAndSaleTimeBetween (
            Long branchId,
            LocalDateTime start,
            LocalDateTime end
    );
}
