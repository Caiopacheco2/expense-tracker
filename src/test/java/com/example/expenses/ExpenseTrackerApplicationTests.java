package com.example.expenses;

import com.example.expenses.model.Category;
import com.example.expenses.model.Transaction;
import com.example.expenses.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExpenseTrackerApplicationTests {

    @Autowired
    TransactionRepository repository;

    @Test
    void contextLoadsAndSaves() {
        Transaction t = Transaction.builder()
                .description("Teste")
                .amount(new BigDecimal("123.45"))
                .date(LocalDate.now())
                .category(Category.FOOD)
                .type(Transaction.Type.EXPENSE)
                .build();
        Transaction saved = repository.save(t);
        assertThat(saved.getId()).isNotNull();
    }
}
