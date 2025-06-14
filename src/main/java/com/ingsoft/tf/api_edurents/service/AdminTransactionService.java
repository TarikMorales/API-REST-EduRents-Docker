package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;

import java.util.List;

public interface AdminTransactionService {

    // HU13
    ShowTransactionDTO crearTransaccion(TransactionDTO transaccionDTO);
    void cancelarTransaccion(Integer id);
    ShowTransactionDTO obtenerTransaccionPorId(Integer id);
    List<ShowTransactionDTO> obtenerTransacciones();
    ShowTransactionDTO obtenerTransaccionPorIdPorUsuario(Integer idTransaction, Integer idUsuario);


}