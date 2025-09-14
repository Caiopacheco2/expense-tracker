package com.example.expenses.dto;

import java.math.BigDecimal;
import java.util.Map;

public record MonthlySummary(
    int year,
    int month,
    BigDecimal totalIncome,
    BigDecimal totalExpense,
    BigDecimal net,
    Map<String, BigDecimal> byCategory
) {}
