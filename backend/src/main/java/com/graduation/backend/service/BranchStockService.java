package com.graduation.backend.service;

import com.graduation.backend.dto.BranchStockCreateRequest;
import com.graduation.backend.dto.BranchStockResponse;
import java.util.List;

public interface BranchStockService {

    BranchStockResponse createOrUpdateStock(BranchStockCreateRequest request);
    List<BranchStockResponse> getStocksByBranch(Long branchId);
}
