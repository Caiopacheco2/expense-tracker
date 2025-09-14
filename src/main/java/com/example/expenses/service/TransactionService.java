package com.example.expenses.service;

import com.example.expenses.dto.MonthlySummary;
import com.example.expenses.model.Transaction;
import com.example.expenses.model.Category;
import com.example.expenses.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    public List<Transaction> search(LocalDate start, LocalDate end, Category category, Transaction.Type type) {
        return repository.search(start, end, category, type);
    }

    public Optional<Transaction> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Transaction create(Transaction t) {
        t.setId(null);
        return repository.save(t);
    }

    @Transactional
    public Transaction update(Long id, Transaction t) {
        Transaction current = repository.findById(id).orElseThrow();
        current.setDescription(t.getDescription());
        current.setAmount(t.getAmount());
        current.setDate(t.getDate());
        current.setCategory(t.getCategory());
        current.setType(t.getType());
        return repository.save(current);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public MonthlySummary monthlySummary(int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        List<Transaction> list = repository.findByDateBetween(start, end);

        BigDecimal income = list.stream().filter(t -> t.getType() == Transaction.Type.INCOME)
                .map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expense = list.stream().filter(t -> t.getType() == Transaction.Type.EXPENSE)
                .map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> byCat = list.stream()
                .filter(t -> t.getType() == Transaction.Type.EXPENSE)
                .collect(Collectors.groupingBy(t -> t.getCategory().name(),
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));

        return new MonthlySummary(year, month, income, expense, income.subtract(expense), byCat);
    }
}
