package com.graduation.backend.service.impl;

import com.graduation.backend.dto.BranchCreateRequest;
import com.graduation.backend.dto.BranchResponse;
import com.graduation.backend.entity.Branch;
import com.graduation.backend.repository.BranchRepository;
import com.graduation.backend.service.BranchService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;

    @Override
    public BranchResponse createBranch(BranchCreateRequest request) {
        if (branchRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Branch already exists with name: " + request.getName());
        }

        Branch branch = Branch.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .active(true)
                .build();

        Branch saved = branchRepository.save(branch);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly=true)
    public BranchResponse getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + id));
        return toResponse(branch);
    }

    @Override
    @Transactional(readOnly=true)
    public List<BranchResponse> getAllBranches() {
        return branchRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public BranchResponse updateBranch(Long id, BranchCreateRequest request) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + id));

        branch.setName(request.getName());
        branch.setAddress(request.getAddress());
        branch.setPhone(request.getPhone());

        Branch updated = branchRepository.save(branch);
        return toResponse(updated);
    }

    @Override
    public void deactivateBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + id));

        branch.setActive(false);
        branchRepository.save(branch);
    }

    private BranchResponse toResponse(Branch branch) {
        return BranchResponse.builder()
                .id(branch.getId())
                .name(branch.getName())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .active(branch.isActive())
                .createdAt(branch.getCreatedAt())
                .updatedAt(branch.getUpdatedAt())
                .build();
    }
}
