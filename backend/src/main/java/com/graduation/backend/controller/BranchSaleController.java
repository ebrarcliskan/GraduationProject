package com.graduation.backend.controller;

import com.graduation.backend.dto.DailySalesSummaryResponse;
import com.graduation.backend.dto.SaleCreateRequest;
import com.graduation.backend.dto.SaleResponse;
import com.graduation.backend.service.BranchSaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class BranchSaleController {

    private final BranchSaleService branchSaleService;

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@Valid @RequestBody SaleCreateRequest request) {
        SaleResponse response = branchSaleService.createSale(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<SaleResponse>> getSalesByBranch(@PathVariable Long branchId) {
        List<SaleResponse> responseList = branchSaleService.getSalesByBranch(branchId);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/branch/{branchId}/summary/daily")
    public ResponseEntity<DailySalesSummaryResponse> getDailySummary(
            @PathVariable Long branchId,
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        DailySalesSummaryResponse response = branchSaleService.getDailySummary(branchId, date);
        return ResponseEntity.ok(response);
    }
}
