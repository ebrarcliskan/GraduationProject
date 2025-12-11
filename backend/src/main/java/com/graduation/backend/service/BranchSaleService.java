package com.graduation.backend.service;

import com.graduation.backend.dto.DailySalesSummaryResponse;
import com.graduation.backend.dto.SaleCreateRequest;
import com.graduation.backend.dto.SaleResponse;
import java.time.LocalDate;
import java.util.List;

public interface BranchSaleService {
    SaleResponse createSale(SaleCreateRequest request);
    List<SaleResponse> getSalesByBranch(Long branchId);
    DailySalesSummaryResponse getDailySummary(Long branchId, LocalDate date);
}
