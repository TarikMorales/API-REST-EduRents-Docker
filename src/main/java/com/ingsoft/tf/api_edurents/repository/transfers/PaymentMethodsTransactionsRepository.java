package com.ingsoft.tf.api_edurents.repository.transfers;

import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethodsTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodsTransactionsRepository extends JpaRepository<PaymentMethodsTransactions, Integer> {
}
