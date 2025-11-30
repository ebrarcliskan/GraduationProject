package com.graduation.backend.controller;

import com.graduation.backend.dto.BranchStockCreateRequest;
import com.graduation.backend.dto.BranchStockResponse;
import com.graduation.backend.service.BranchStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class BranchStockController {

    private final BranchStockService branchStockService;

    @PostMapping
    public ResponseEntity<BranchStockResponse> createOrUpdateStock(@Valid @RequestBody BranchStockCreateRequest request){
        BranchStockResponse response = branchStockService.createOrUpdateStock(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<BranchStockResponse>> getStocksByBranch(@PathVariable Long branchId){
        List<BranchStockResponse> responseList = branchStockService.getStocksByBranch(branchId);
        return ResponseEntity.ok(responseList);
    }
}
