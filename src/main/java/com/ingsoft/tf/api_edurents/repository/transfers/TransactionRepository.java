package com.ingsoft.tf.api_edurents.repository.transfers;

import com.ingsoft.tf.api_edurents.model.entity.transfers.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
