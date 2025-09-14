package com.example.expenses.repository;

import com.example.expenses.model.Transaction;
import com.example.expenses.model.Category;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByDateBetween(LocalDate start, LocalDate end);
    List<Transaction> findByCategory(Category category);
    List<Transaction> findByType(Transaction.Type type);

    @Query("select t from Transaction t " +
           "where (:start is null or t.date >= :start) " +
           "and (:end is null or t.date <= :end) " +
           "and (:category is null or t.category = :category) " +
           "and (:type is null or t.type = :type)")
    List<Transaction> search(@Param("start") LocalDate start,
                             @Param("end") LocalDate end,
                             @Param("category") Category category,
                             @Param("type") Transaction.Type type);
}
