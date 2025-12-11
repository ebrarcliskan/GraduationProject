package com.graduation.backend.service.impl;

import com.graduation.backend.dto.DailySalesSummaryResponse;
import com.graduation.backend.dto.SaleCreateRequest;
import com.graduation.backend.dto.SaleResponse;
import com.graduation.backend.entity.Branch;
import com.graduation.backend.entity.BranchSale;
import com.graduation.backend.entity.BranchStock;
import com.graduation.backend.entity.Product;
import com.graduation.backend.repository.BranchRepository;
import com.graduation.backend.repository.BranchSaleRepository;
import com.graduation.backend.repository.BranchStockRepository;
import com.graduation.backend.repository.ProductRepository;
import com.graduation.backend.service.BranchSaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchSaleServiceImpl implements BranchSaleService {
    private final BranchSaleRepository branchSaleRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final BranchStockRepository branchStockRepository;

    @Override
    public SaleResponse createSale(SaleCreateRequest request) {
        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + request.getBranchId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + request.getProductId()));

        BranchStock stock = branchStockRepository.findByBranchIdAndProductId(branch.getId(), product.getId())
                .orElseThrow(() -> new IllegalArgumentException("No stock record for this branch and product"));

        if (stock.getQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock.Current: " + stock.getQuantity());
        }

        stock.setQuantity(stock.getQuantity() - request.getQuantity());
        branchStockRepository.save(stock);

        BigDecimal unitPrice = product.getUnitPrice();
        BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(request.getQuantity()));

        BranchSale sale = BranchSale.builder()
                .branch(branch)
                .product(product)
                .quantity(request.getQuantity())
                .unitPrice(unitPrice)
                .totalAmount(totalAmount)
                .saleTime(LocalDateTime.now())
                .build();

        BranchSale saved = branchSaleRepository.save(sale);

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponse> getSalesByBranch(Long branchId) {
        List<BranchSale> sales = branchSaleRepository.findAllByBranchId(branchId);

        return sales.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DailySalesSummaryResponse getDailySummary(Long branchId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        List<BranchSale> sales = branchSaleRepository.findAllByBranchIdAndSaleTimeBetween(branchId, start, end);

        BigDecimal totalSalesAmount = sales.stream()
                .map(BranchSale::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalQuantity = sales.stream().mapToInt(BranchSale::getQuantity).sum();

        String branchName = sales.isEmpty() ? null : sales.get(0).getBranch().getName();

        return DailySalesSummaryResponse.builder()
                .branchId(branchId)
                .branchName(branchName)
                .date(date)
                .totalSalesAmount(totalSalesAmount)
                .totalQuantity(totalQuantity)
                .build();
    }

    private SaleResponse toResponse(BranchSale sale) {

        return SaleResponse.builder()
                .id(sale.getId())
                .branchId(sale.getBranch().getId())
                .branchName(sale.getBranch().getName())
                .productId(sale.getProduct().getId())
                .productName(sale.getProduct().getName())
                .productSku(sale.getProduct().getSku())
                .quantity(sale.getQuantity())
                .unitPrice(sale.getUnitPrice())
                .totalAmount(sale.getTotalAmount())
                .saleTime(sale.getSaleTime())
                .build();
    }

}
