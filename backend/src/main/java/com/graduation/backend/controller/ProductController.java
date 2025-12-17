package com.graduation.backend.controller;

import com.graduation.backend.dto.ProductCreateRequest;
import com.graduation.backend.dto.ProductResponse;
import com.graduation.backend.service.ProductService;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product management APIs")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product", description = "Register a new product in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request){
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of products retrieved successfully")
    })
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> responseList = productService.getAllProducts();
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductCreateRequest request){
        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate product", description = "Deactivate a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deactivateProduct(@PathVariable Long id){
        productService.deactivateProduct(id);
        return ResponseEntity.noContent().build();
    }

}




