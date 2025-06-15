package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ClaimTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;

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
    private ProductMapper productMapper;

    @Autowired
    private final TransactionsMapper transactionsMapper;

    // HU14

    @Transactional()
    @Override
    public ShowTransactionDTO confirmarEntregaPago(Integer idTransaccion) {
        Transaction transaction = transactionRepository.findById(idTransaccion)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada"));

        if (transaction.getEstado() == TransactionStatus.CANCELADO) {
            throw new BadRequestException("No se puede confirmar una transacción cancelada.");
        }


        }

        transaction.setEstado(TransactionStatus.PAGADO);
        transaction.setFecha_confirmacion_entrega(LocalDateTime.now());

        transaction = transactionRepository.save(transaction);
        return transactionsMapper.toResponse(transaction);
    }


    @Transactional()
    @Override
    public ShowTransactionDTO reclamarTransaccion(Integer id, ClaimTransactionDTO dto) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada"));

        if (transaction.getEstado() == TransactionStatus.CANCELADO) {
            throw new BadRequestException("No se puede reclamar una transacción cancelada.");
        }

        if (transaction.getEstado() == TransactionStatus.PAGADO) {
            throw new BadRequestException("No se puede reclamar una transacción que ya fue confirmada como entregada.");
        }

        if (transaction.getEstado() == TransactionStatus.RECLAMO_ENVIADO) {
            throw new BadRequestException("Ya existe un reclamo enviado para esta transacción.");
        }

        transaction.setEstado(TransactionStatus.RECLAMO_ENVIADO);
        transaction.setMotivo_reclamo(dto.getMotivo_reclamo());
        transaction.setFecha_confirmacion_entrega(LocalDateTime.now());
    private Transaction convertToTransaction(Transaction transaction, TransactionDTO transaccionDTO, String tipo) {
        transaction.setFecha_transaccion(LocalDate.now().atStartOfDay());


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

    @Override
    public ShowTransactionDTO obtenerTransaccionPorProductoYUsuario(Integer idProducto, Integer idUsuario) {
        Transaction transaction = transactionRepository.findByProductoIdAndUsuarioId(idProducto, idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una transacción para este producto y usuario."));

        return transactionsMapper.toResponse(transaction);
    }

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorProductoYVendedor(Integer idProducto, Integer idVendedor) {
        return transactionRepository.findByProductoIdAndProductoVendedorId(idProducto, idVendedor)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }


    // Fin HU14



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
