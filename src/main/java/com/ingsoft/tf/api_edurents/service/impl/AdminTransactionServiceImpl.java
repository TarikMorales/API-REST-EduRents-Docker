package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.TransactionsMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.Transaction;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.transfers.TransactionRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.AdminTransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminTransactionServiceImpl implements AdminTransactionService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final TransactionsMapper transactionsMapper;

    // HU15

    // Como usuario
    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorUsuario(Integer idUser) {
        return transactionRepository.findByUsuarioId(idUser)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorEstado(Integer idUser, TransactionStatus estado) {
        return transactionRepository.findByUsuarioIdAndEstado(idUser, estado)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorMetodoPago(Integer idUser, PaymentMethod metodo) {
        return transactionRepository.findByUsuarioIdAndMetodoPago(idUser, metodo)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorMetodoPagoPorEstado(Integer idUser, PaymentMethod metodo, TransactionStatus estado) {
        return transactionRepository.findByUsuarioIdAndMetodoPagoAndEstado(idUser, metodo, estado)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Como vendedor
    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorVendedor(Integer idSeller) {
        return transactionRepository.findByProducto_Vendedor_Id(idSeller)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorVendedorPorEstado(Integer idSeller, TransactionStatus estado) {
        return transactionRepository.findByProducto_Vendedor_IdAndEstado(idSeller, estado)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorVendedorPorMetodoPago(Integer idSeller, PaymentMethod metodo) {
        return transactionRepository.findByProducto_Vendedor_IdAndMetodoPago(idSeller, metodo)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorVendedorPorMetodoPagoPorEstado(Integer idSeller, PaymentMethod metodo, TransactionStatus estado) {
        return transactionRepository.findByProducto_Vendedor_IdAndMetodoPagoAndEstado(idSeller, metodo, estado)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

}
