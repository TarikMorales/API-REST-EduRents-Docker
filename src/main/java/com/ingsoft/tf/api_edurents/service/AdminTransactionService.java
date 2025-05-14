package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.Transaction;

public interface AdminTransactionService {
    ShowTransactionDTO crearTransaccion(TransactionDTO transaccionDTO);
}
