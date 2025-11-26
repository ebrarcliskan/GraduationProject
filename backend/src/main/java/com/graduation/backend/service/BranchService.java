package com.graduation.backend.service;

import com.graduation.backend.dto.BranchCreateRequest;
import com.graduation.backend.dto.BranchResponse;
import java.util.List;

public interface BranchService {
    BranchResponse createBranch(BranchCreateRequest request);
    BranchResponse getBranchById(Long id);
    List<BranchResponse> getAllBranches();
    BranchResponse updateBranch(Long id, BranchCreateRequest request);
    void deactivateBranch(Long id);
}
