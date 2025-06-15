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


    // HU 15

    //endpoint de obtener transacciones por ID usuario

    @Test
    @DisplayName("CP01 - Obtener transacciones por ID de usuario con datos")
    void obtenerTransaccionesUsuario_conDatos_devuelveLista() {
        Transaction t1 = new Transaction();
        Transaction t2 = new Transaction();
        List<Transaction> transactions = List.of(t1, t2);

        ShowTransactionDTO dto1 = new ShowTransactionDTO();
        ShowTransactionDTO dto2 = new ShowTransactionDTO();

        when(transactionRepository.findByUsuarioId(1)).thenReturn(transactions);
        when(transactionsMapper.toResponse(t1)).thenReturn(dto1);
        when(transactionsMapper.toResponse(t2)).thenReturn(dto2);

        List<ShowTransactionDTO> result = userAuthTransactionServiceImpl.obtenerTransaccionesPorUsuario(1);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("CP02 - Obtener transacciones por ID de usuario sin datos")
    void obtenerTransaccionesUsuario_sinDatos_devuelveListaVacia() {
        when(transactionRepository.findByUsuarioId(1)).thenReturn(Collections.emptyList());
        List<ShowTransactionDTO> result = userAuthTransactionServiceImpl.obtenerTransaccionesPorUsuario(1);
        assertTrue(result.isEmpty());
    }

    //endpoint de filtrado de transacciones usuario por estado

    @Test
    @DisplayName("CP01 - Obtener transacciones por estado del usuario")
    void obtenerTransaccionesUsuario_estadoFiltrado_devuelveLista() {
        Transaction t1 = new Transaction();
        List<Transaction> transactions = List.of(t1);
        ShowTransactionDTO dto1 = new ShowTransactionDTO();

        when(transactionRepository.findByUsuarioIdAndEstado(2, TransactionStatus.PAGADO)).thenReturn(transactions);
        when(transactionsMapper.toResponse(t1)).thenReturn(dto1);

        List<ShowTransactionDTO> result = userAuthTransactionServiceImpl
                .obtenerTransaccionesPorUsuarioPorEstado(2, TransactionStatus.PAGADO);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("CP02 - Obtener transacciones del usuario con estado sin coincidencias")
    void obtenerTransaccionesUsuario_estadoSinCoincidencia_listaVacia() {
        when(transactionRepository.findByUsuarioIdAndEstado(2, TransactionStatus.CANCELADO))
                .thenReturn(Collections.emptyList());

        List<ShowTransactionDTO> result = userAuthTransactionServiceImpl
                .obtenerTransaccionesPorUsuarioPorEstado(2, TransactionStatus.CANCELADO);

        assertTrue(result.isEmpty());
    }

}