package com.graduation.backend.controller;

import com.graduation.backend.dto.BranchCreateRequest;
import com.graduation.backend.dto.BranchResponse;
import com.graduation.backend.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
@Tag(name = "Branches", description = "Branch management APIs")
@SecurityRequirement(name = "bearerAuth")
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    @Operation(summary = "Create a new branch", description = "Register a new branch in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Branch created successfully",
                    content = @Content(schema = @Schema(implementation = BranchResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<BranchResponse> createBranch(@Valid @RequestBody BranchCreateRequest request){
        BranchResponse response = branchService.createBranch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get branch by ID", description = "Retrieve a specific branch by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch found",
                    content = @Content(schema = @Schema(implementation = BranchResponse.class))),
            @ApiResponse(responseCode = "404", description = "Branch not found")
    })
    public ResponseEntity<BranchResponse> getBranchById(@PathVariable Long id){
        BranchResponse response = branchService.getBranchById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all branches", description = "Retrieve a list of all branches")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of branches retrieved successfully")
    })
    public ResponseEntity<List<BranchResponse>> getAllBranches(){
        List<BranchResponse> responseList = branchService.getAllBranches();
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update branch", description = "Update an existing branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch updated successfully",
                    content = @Content(schema = @Schema(implementation = BranchResponse.class))),
            @ApiResponse(responseCode = "404", description = "Branch not found")
    })
    public ResponseEntity<BranchResponse> updateBranch(@PathVariable Long id,
                                                       @Valid @RequestBody BranchCreateRequest request){
        BranchResponse response = branchService.updateBranch(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate branch", description = "Deactivate a branch by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Branch deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Branch not found")
    })
    public ResponseEntity<Void> deactivateBranch(@PathVariable Long id){
        branchService.deactivateBranch(id);
        return ResponseEntity.noContent().build();
    }
}
