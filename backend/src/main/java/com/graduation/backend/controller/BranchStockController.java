package com.graduation.backend.controller;

import com.graduation.backend.dto.BranchStockCreateRequest;
import com.graduation.backend.dto.BranchStockResponse;
import com.graduation.backend.service.BranchStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@Tag(name = "Branch Stocks", description = "Branch stock management APIs")
@SecurityRequirement(name = "bearerAuth")
public class BranchStockController {

    private final BranchStockService branchStockService;

    @PostMapping
    @Operation(summary = "Create or update stock", description = "Create a new stock entry or update existing stock for a branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock created/updated successfully",
                    content = @Content(schema = @Schema(implementation = BranchStockResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Branch or product not found")
    })
    public ResponseEntity<BranchStockResponse> createOrUpdateStock(@Valid @RequestBody BranchStockCreateRequest request){
        BranchStockResponse response = branchStockService.createOrUpdateStock(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/{branchId}")
    @Operation(summary = "Get stocks by branch", description = "Retrieve all stock entries for a specific branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stocks retrieved successfully")
    })
    public ResponseEntity<List<BranchStockResponse>> getStocksByBranch(@PathVariable Long branchId){
        List<BranchStockResponse> responseList = branchStockService.getStocksByBranch(branchId);
        return ResponseEntity.ok(responseList);
    }
}
