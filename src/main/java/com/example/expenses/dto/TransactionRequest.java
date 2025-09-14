package com.example.expenses.dto;

import com.example.expenses.model.Category;
import com.example.expenses.model.Transaction;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(
        @NotBlank String description,
        @NotNull @DecimalMin(value="0.00", inclusive = true) BigDecimal amount,
        @NotNull LocalDate date,
        @NotNull Category category,
        @NotNull Transaction.Type type
) {}
