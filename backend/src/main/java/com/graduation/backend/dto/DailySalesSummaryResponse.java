package com.graduation.backend.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class DailySalesSummaryResponse {
    private Long branchId;
    private String branchName;
    private LocalDate date;
    private BigDecimal totalSalesAmount;
    private Integer totalQuantity;
}
