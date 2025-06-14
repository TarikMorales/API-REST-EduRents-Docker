package com.ingsoft.tf.api_edurents.service.auth.user;

import com.ingsoft.tf.api_edurents.dto.transfers.ClaimTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.TransactionsMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.Transaction;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.transfers.TransactionRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.impl.auth.user.UserAuthTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserAuthTransactionServiceUnitTest {

    @Mock private ProductRepository productRepository;
    @Mock private TransactionRepository transactionRepository;
    @Mock private UserRepository userRepository;
    @Mock private TransactionsMapper transactionsMapper;

    @InjectMocks
    private UserAuthTransactionServiceImpl userAuthTransactionServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //HU 13

    // Endpoint de crear transaccion

    @Test
    @DisplayName("CP01 - Crear transacción con datos válidos")
    void crearTransaccion_valida_devuelveTransaccion() {
        TransactionDTO request = new TransactionDTO();
        request.setId_producto(1);
        request.setId_usuario(2);
        request.setMetodo_pago(PaymentMethod.EFECTIVO);

        User user = new User(); user.setId(2);

        Seller seller = new Seller();
        User sellerUser = new User(); sellerUser.setId(1);
        seller.setUsuario(sellerUser);

        Product product = new Product();
        product.setId(1);
        product.setVendedor(seller);

        Transaction saved = new Transaction();
        saved.setId(10);
        saved.setUsuario(user);
        saved.setProducto(product);

        ShowTransactionDTO response = new ShowTransactionDTO();
        response.setId(10);

        when(userRepository.findById(2)).thenReturn(Optional.of(user));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(transactionRepository.existsByProductoIdAndUsuarioId(1, 2)).thenReturn(false);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(saved);
        when(transactionsMapper.toResponse(saved)).thenReturn(response);

        ShowTransactionDTO result = userAuthTransactionServiceImpl.crearTransaccion(request);
        assertNotNull(result);
        assertEquals(10, result.getId());
    }

    @Test
    @DisplayName("CP02 - No se encuentra el usuario al crear transacción")
    void crearTransaccion_usuarioNoEncontrado_lanzaExcepcion() {
        TransactionDTO request = new TransactionDTO();
        request.setId_producto(1);
        request.setId_usuario(22);
        request.setMetodo_pago(PaymentMethod.EFECTIVO);

        when(userRepository.findById(22)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userAuthTransactionServiceImpl.crearTransaccion(request);
        });
    }

    @Test
    @DisplayName("CP03 - Usuario ya ha hecho una transacción sobre el producto")
    void crearTransaccion_duplicadaPorUsuarioYProducto_lanzaExcepcion() {
        TransactionDTO request = new TransactionDTO();
        request.setId_producto(1);
        request.setId_usuario(2);
        request.setMetodo_pago(PaymentMethod.EFECTIVO);

        User user = new User();
        user.setId(2);
        when(userRepository.findById(2)).thenReturn(Optional.of(user));

        Seller seller = new Seller();
        User sellerUser = new User(); sellerUser.setId(99);
        seller.setUsuario(sellerUser);
        Product product = new Product();
        product.setId(1);
        product.setVendedor(seller);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        when(transactionRepository.existsByProductoIdAndUsuarioId(1, 2)).thenReturn(true);

        assertThrows(BadRequestException.class, () -> {
            userAuthTransactionServiceImpl.crearTransaccion(request);
        });
    }

    // Endpoint de cancelar transaccion

    @Test
    @DisplayName("CP01 - Cancelar transacción existente")
    void cancelarTransaccion_existente_devuelveMensaje() {
        Transaction transaction = new Transaction();
        transaction.setId(5);
        transaction.setEstado(TransactionStatus.PENDIENTE);

        when(transactionRepository.findById(5)).thenReturn(Optional.of(transaction));

        userAuthTransactionServiceImpl.cancelarTransaccion(5);

        assertEquals(TransactionStatus.CANCELADO, transaction.getEstado());

        verify(transactionRepository).save(transaction);
    }

    @Test
    @DisplayName("CP02 - Cancelar transacción inexistente")
    void cancelarTransaccion_inexistente_lanzaExcepcion() {
        when(transactionRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userAuthTransactionServiceImpl.cancelarTransaccion(99));
    }

    // Endpoint de obtener transaccion por ID y por ID de usuario

    @Test
    @DisplayName("CP01 - Obtener transacción por usuario válida")
    void obtenerTransaccionUsuario_valida_devuelveDTO() {
        Transaction transaction = new Transaction();
        transaction.setId(7);
        User user = new User(); user.setId(1);
        transaction.setUsuario(user);
        ShowTransactionDTO response = new ShowTransactionDTO(); response.setId(7);

        when(transactionRepository.findByIdAndUsuarioId(7, 1)).thenReturn(Optional.of(transaction));
        when(transactionsMapper.toResponse(transaction)).thenReturn(response);

        ShowTransactionDTO result = userAuthTransactionServiceImpl.obtenerTransaccionPorIdPorUsuario(7, 1);

        assertEquals(7, result.getId());
    }


    @Test
    @DisplayName("CP02 - Obtener transacción no perteneciente al usuario")
    void obtenerTransaccionUsuario_noPertenece_lanzaExcepcion() {
        when(transactionRepository.findByIdAndUsuarioId(8, 1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userAuthTransactionServiceImpl.obtenerTransaccionPorIdPorUsuario(8, 1);
        });
    }

}