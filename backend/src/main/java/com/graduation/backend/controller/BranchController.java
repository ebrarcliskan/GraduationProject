package com.graduation.backend.controller;

import com.graduation.backend.dto.BranchCreateRequest;
import com.graduation.backend.dto.BranchResponse;
import com.graduation.backend.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchResponse> createBranch(@Valid @RequestBody BranchCreateRequest request){
        BranchResponse response = branchService.createBranch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchResponse> getBranchById(@PathVariable Long id){
        BranchResponse response = branchService.getBranchById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BranchResponse>> getAllBranches(){
        List<BranchResponse> responseList = branchService.getAllBranches();
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchResponse> updateBranch(@PathVariable Long id,
                                                       @Valid @RequestBody BranchCreateRequest request){
        BranchResponse response = branchService.updateBranch(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateBranch(@PathVariable Long id){
        branchService.deactivateBranch(id);
        return ResponseEntity.noContent().build();
    }
}
