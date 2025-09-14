package com.example.expenses.controller;

import com.example.expenses.dto.MonthlySummary;
import com.example.expenses.dto.TransactionRequest;
import com.example.expenses.model.Transaction;
import com.example.expenses.model.Category;
import com.example.expenses.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class ApiController {

    private final TransactionService service;

    @GetMapping("/transactions")
    public List<Transaction> list(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Transaction.Type type
    ) {
        return service.search(startDate, endDate, category, type);
    }

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction create(@Valid @RequestBody TransactionRequest req) {
        Transaction t = Transaction.builder()
                .description(req.description())
                .amount(req.amount())
                .date(req.date())
                .category(req.category())
                .type(req.type())
                .build();
        return service.create(t);
    }

    @PutMapping("/transactions/{id}")
    public Transaction update(@PathVariable Long id, @Valid @RequestBody TransactionRequest req) {
        Transaction t = Transaction.builder()
                .description(req.description())
                .amount(req.amount())
                .date(req.date())
                .category(req.category())
                .type(req.type())
                .build();
        return service.update(id, t);
    }

    @DeleteMapping("/transactions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/summary/monthly")
    public MonthlySummary monthly(@RequestParam int year, @RequestParam int month) {
        return service.monthlySummary(year, month);
    }
}
