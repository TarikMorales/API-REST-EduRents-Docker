package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
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

    private ShowTransactionDTO convertShowTransactionDTO(Transaction transaccion) {

        ShowTransactionDTO transaccionDTOMostrar = new ShowTransactionDTO();

        transaccionDTOMostrar.setId(transaccion.getId());
        transaccionDTOMostrar.setFecha_transaccion(transaccion.getFecha_transaccion());
        transaccionDTOMostrar.setEstado(transaccion.getEstado());
        transaccionDTOMostrar.setMetodo_pago(transaccion.getMetodo_pago());

        // Asignar Usuario
        if (transaccion.getUsuario() != null) {
            UserDTO usuarioDTO = new UserDTO();
            usuarioDTO.setId(transaccion.getUsuario().getId());
            usuarioDTO.setNombres(transaccion.getUsuario().getNombres());
            usuarioDTO.setApellidos(transaccion.getUsuario().getApellidos());
            usuarioDTO.setCorreo(transaccion.getUsuario().getCorreo());
            usuarioDTO.setCodigo_universitario(transaccion.getUsuario().getCodigo_universitario());
            usuarioDTO.setCiclo(transaccion.getUsuario().getCiclo());

            transaccionDTOMostrar.setUsuario(usuarioDTO);
        }

        // Asignar Producto
        if (transaccion.getProducto() != null) {
            Product producto = transaccion.getProducto();
            ShowProductDTO productoDTO = productMapper.toResponse(producto);

            transaccionDTOMostrar.setProducto(productoDTO);
        }

        return transaccionDTOMostrar;

    }

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
