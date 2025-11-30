package com.graduation.backend.service.impl;

import com.graduation.backend.dto.BranchStockCreateRequest;
import com.graduation.backend.dto.BranchStockResponse;
import com.graduation.backend.entity.Branch;
import com.graduation.backend.entity.BranchStock;
import com.graduation.backend.entity.Product;
import com.graduation.backend.repository.BranchRepository;
import com.graduation.backend.repository.BranchStockRepository;
import com.graduation.backend.repository.ProductRepository;
import com.graduation.backend.service.BranchStockService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchStockServiceImpl implements BranchStockService {

    private final BranchStockRepository branchStockRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    @Override
    public BranchStockResponse createOrUpdateStock(BranchStockCreateRequest request) {
        Branch branch = branchRepository.findById(request.getBranchId()).orElseThrow(() ->
                new EntityNotFoundException("Branch not found with id: " + request.getBranchId()));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() ->
                new EntityNotFoundException("Product not found with id: " + request.getProductId()));
        BranchStock branchStock = branchStockRepository.findByBranchIdAndProductId(branch.getId(), product.getId())
                .orElse(
                        BranchStock.builder()
                                .branch(branch)
                                .product(product)
                                .quantity(0)
                                .build()
                );
        branchStock.setQuantity(request.getQuantity());
        BranchStock saved = branchStockRepository.save(branchStock);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchStockResponse> getStocksByBranch(Long branchId) {
        List<BranchStock> stocks = branchStockRepository.findAllByBranchId(branchId);
        return stocks.stream()
                .map(this::toResponse)
                .toList();
    }

    private BranchStockResponse toResponse(BranchStock stock) {
        return BranchStockResponse.builder()
                .id(stock.getId())
                .branchId(stock.getBranch().getId())
                .branchName(stock.getBranch().getName())
                .productId(stock.getProduct().getId())
                .productName(stock.getProduct().getName())
                .productSku(stock.getProduct().getSku())
                .quantity(stock.getQuantity())
                .createdAt(stock.getCreatedAt())
                .updatedAt(stock.getUpdatedAt())
                .build();
    }


}
