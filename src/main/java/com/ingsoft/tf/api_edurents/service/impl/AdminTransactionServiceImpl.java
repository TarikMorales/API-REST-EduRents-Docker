package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.TransactionsMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
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
    private AdminProductServiceImpl adminProductService;

    @Autowired
    private final TransactionsMapper transactionsMapper;

    @Transactional
    @Override
    public ShowTransactionDTO crearTransaccion(TransactionDTO transaccionDTO) {

        User user = userRepository.findById(transaccionDTO.getId_usuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Product product = productRepository.findById(transaccionDTO.getId_producto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        if (product.getVendedor().getUsuario().getId().equals(user.getId())) {
            throw new BadRequestException("No puedes realizar una transacción con tu propio producto.");
        }

        Transaction transaction = new Transaction();
        transaction.setUsuario(user);
        transaction.setProducto(product);
        transaction.setMetodoPago(transaccionDTO.getMetodo_pago());
        transaction.setEstado(TransactionStatus.PENDIENTE);
        transaction.setFecha_transaccion(LocalDateTime.now());

        transaction = transactionRepository.save(transaction);

        return transactionsMapper.toResponse(transaction);
    }


    @Transactional()
    @Override
    public void cancelarTransaccion(Integer id){
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaccion no encontrada con id: " + id));

        transaction.setEstado(TransactionStatus.CANCELADO);
        transactionRepository.save(transaction);
    }

    @Transactional()
    @Override
    public ShowTransactionDTO obtenerTransaccionPorId(Integer id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada"));

        return transactionsMapper.toResponse(transaction);
    }

    @Transactional()
    @Override
    public List<ShowTransactionDTO> obtenerTransacciones() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ShowTransactionDTO obtenerTransaccionPorIdPorUsuario(Integer idTransaction, Integer idUsuario) {
        Transaction transaction = transactionRepository.findByIdAndUsuarioId(idTransaction, idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la transacción con ese usuario"));
        return transactionsMapper.toResponse(transaction);
    }


}
