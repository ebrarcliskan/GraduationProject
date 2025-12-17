package com.graduation.backend.controller;

import com.graduation.backend.dto.DailySalesSummaryResponse;
import com.graduation.backend.dto.SaleCreateRequest;
import com.graduation.backend.dto.SaleResponse;
import com.graduation.backend.service.BranchSaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Branch Sales", description = "Branch sales management APIs")
@SecurityRequirement(name = "bearerAuth")
public class BranchSaleController {

    private final BranchSaleService branchSaleService;

    @PostMapping
    @Operation(summary = "Create a new sale", description = "Record a new sale transaction. Stock will be automatically decreased.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale created successfully",
                    content = @Content(schema = @Schema(implementation = SaleResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data or insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Branch or product not found")
    })
    public ResponseEntity<SaleResponse> createSale(@Valid @RequestBody SaleCreateRequest request) {
        SaleResponse response = branchSaleService.createSale(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/{branchId}")
    @Operation(summary = "Get sales by branch", description = "Retrieve all sales for a specific branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sales retrieved successfully")
    })
    public ResponseEntity<List<SaleResponse>> getSalesByBranch(@PathVariable Long branchId) {
        List<SaleResponse> responseList = branchSaleService.getSalesByBranch(branchId);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/branch/{branchId}/summary/daily")
    @Operation(summary = "Get daily sales summary", description = "Get a summary of sales for a specific branch on a specific date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Summary retrieved successfully",
                    content = @Content(schema = @Schema(implementation = DailySalesSummaryResponse.class)))
    })
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
