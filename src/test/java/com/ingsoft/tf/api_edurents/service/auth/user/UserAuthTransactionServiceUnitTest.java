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

    // HU 14

    // Endpoint confirmar entrega

    @Test
    @DisplayName("CP01 - Confirmar entrega con ID válido")
    void confirmarEntrega_transaccionExistente_devuelveActualizado() {
        Transaction transaccion = new Transaction();
        transaccion.setId(1);
        transaccion.setEstado(TransactionStatus.PENDIENTE);

        ShowTransactionDTO dto = new ShowTransactionDTO();
        dto.setId(1);
        dto.setEstado(TransactionStatus.PAGADO);

        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaccion));
        when(transactionRepository.save(transaccion)).thenReturn(transaccion);
        when(transactionsMapper.toResponse(transaccion)).thenReturn(dto);

        ShowTransactionDTO result = userAuthTransactionServiceImpl.confirmarEntregaPago(1);

        assertEquals(TransactionStatus.PAGADO, result.getEstado());
        verify(transactionRepository).save(transaccion);
    }

    @Test
    @DisplayName("CP02 - Confirmar entrega con ID inválido")
    void confirmarEntrega_transaccionNoExistente_lanzaExcepcion() {
        when(transactionRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> userAuthTransactionServiceImpl.confirmarEntregaPago(99));
    }

    @Test
    @DisplayName("CP03 - Confirmar entrega fallido si transacción está cancelada")
    void confirmarEntrega_transaccionCancelada_lanzaExcepcion() {
        Transaction transaccion = new Transaction();
        transaccion.setId(2);
        transaccion.setEstado(TransactionStatus.CANCELADO);

        when(transactionRepository.findById(2)).thenReturn(Optional.of(transaccion));

        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                userAuthTransactionServiceImpl.confirmarEntregaPago(2));

        assertEquals("No se puede confirmar una transacción cancelada.", exception.getMessage());
    }

    // Endpoint mandar reclamo

    @Test
    @DisplayName("CP01 - Presentar reclamo con datos válidos")
    void reclamarTransaccion_valido_devuelveActualizado() {
        Transaction transaccion = new Transaction();
        transaccion.setId(4);
        transaccion.setEstado(TransactionStatus.PENDIENTE);

        ClaimTransactionDTO dtoRequest = new ClaimTransactionDTO();
        dtoRequest.setMotivo_reclamo("Producto en mal estado");

        ShowTransactionDTO dtoResponse = new ShowTransactionDTO();
        dtoResponse.setId(4);
        dtoResponse.setMotivo_reclamo("Producto en mal estado");
        dtoResponse.setEstado(TransactionStatus.RECLAMO_ENVIADO);

        when(transactionRepository.findById(4)).thenReturn(Optional.of(transaccion));
        when(transactionRepository.save(transaccion)).thenReturn(transaccion);
        when(transactionsMapper.toResponse(transaccion)).thenReturn(dtoResponse);

        ShowTransactionDTO result = userAuthTransactionServiceImpl.reclamarTransaccion(4, dtoRequest);

        assertEquals(TransactionStatus.RECLAMO_ENVIADO, result.getEstado());
        assertEquals("Producto en mal estado", result.getMotivo_reclamo());
        verify(transactionRepository).save(transaccion);
    }

    @Test
    @DisplayName("CP02 - Presentar reclamo para transacción inexistente")
    void reclamarTransaccion_noExiste_lanzaExcepcion() {
        ClaimTransactionDTO dto = new ClaimTransactionDTO();
        dto.setMotivo_reclamo("Producto en mal estado");

        when(transactionRepository.findById(3)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                userAuthTransactionServiceImpl.reclamarTransaccion(3, dto));
    }


    @Test
    @DisplayName("CP03 - Reclamo fallido si transacción ya fue confirmada")
    void reclamarTransaccion_transaccionPagada_lanzaExcepcion() {
        Transaction transaccion = new Transaction();
        transaccion.setId(3);
        transaccion.setEstado(TransactionStatus.PAGADO); // Ya confirmada

        ClaimTransactionDTO dto = new ClaimTransactionDTO();
        dto.setMotivo_reclamo("Producto dañado");


        when(transactionRepository.findById(3)).thenReturn(Optional.of(transaccion));

        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                userAuthTransactionServiceImpl.reclamarTransaccion(3, dto));

        assertEquals("No se puede reclamar una transacción que ya fue confirmada como entregada.", exception.getMessage());
    }
}