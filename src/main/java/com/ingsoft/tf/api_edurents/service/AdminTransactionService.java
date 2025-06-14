package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;

import java.util.List;

public interface AdminTransactionService {

    List<ShowTransactionDTO> obtenerTransaccionesPorUsuario(Integer idUser);
    List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorEstado(Integer idUser, TransactionStatus estado);
    List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorMetodoPago(Integer idUser, PaymentMethod metodo);
    List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorMetodoPagoPorEstado(Integer idUser, PaymentMethod metodo, TransactionStatus estado);

    List<ShowTransactionDTO> obtenerTransaccionesPorVendedor(Integer idSeller);
    List<ShowTransactionDTO> obtenerTransaccionesPorVendedorPorEstado(Integer idSeller, TransactionStatus estado);
    List<ShowTransactionDTO> obtenerTransaccionesPorVendedorPorMetodoPago(Integer idSeller, PaymentMethod metodo);
    List<ShowTransactionDTO> obtenerTransaccionesPorVendedorPorMetodoPagoPorEstado(Integer idSeller, PaymentMethod metodo, TransactionStatus estado);


}
