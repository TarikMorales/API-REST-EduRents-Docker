package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.transfers.ClaimTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;

import java.util.List;

public interface AdminTransactionService {

    // hu14
    ShowTransactionDTO confirmarEntregaPago(Integer idTransaccion);
    ShowTransactionDTO reclamarTransaccion(Integer id, ClaimTransactionDTO dto);
    ShowTransactionDTO obtenerTransaccionPorProductoYUsuario(Integer idProducto, Integer idUsuario);
    List<ShowTransactionDTO> obtenerTransaccionesPorProductoYVendedor(Integer idProducto, Integer idVendedor);


}
