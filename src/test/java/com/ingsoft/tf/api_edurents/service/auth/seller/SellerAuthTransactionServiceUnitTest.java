package com.ingsoft.tf.api_edurents.service.auth.seller;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.mapper.TransactionsMapper;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.Transaction;
import com.ingsoft.tf.api_edurents.repository.transfers.TransactionRepository;
import com.ingsoft.tf.api_edurents.service.impl.auth.seller.SellerAuthTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class SellerAuthTransactionServiceUnitTest {

    @Mock private TransactionRepository transactionRepository;
    @Mock private TransactionsMapper transactionsMapper;

    @InjectMocks
    private SellerAuthTransactionServiceImpl sellerAuthTransactionServiceimpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // HU15

    // endpoint de transacciones vendedor por metodo pago

    @Test
    @DisplayName("CP05 - Obtener transacciones del vendedor por método de pago")
    void obtenerTransaccionesVendedor_porMetodoPago_devuelveLista() {
        Transaction t1 = new Transaction();
        List<Transaction> transactions = List.of(t1);
        ShowTransactionDTO dto1 = new ShowTransactionDTO();

        when(transactionRepository.findByProducto_Vendedor_IdAndMetodoPago(3, PaymentMethod.EFECTIVO))
                .thenReturn(transactions);
        when(transactionsMapper.toResponse(t1)).thenReturn(dto1);

        List<ShowTransactionDTO> result = sellerAuthTransactionServiceimpl
                .obtenerTransaccionesPorVendedorPorMetodoPago(3, PaymentMethod.EFECTIVO);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("CP06 - Obtener transacciones del vendedor sin método de pago coincidente")
    void obtenerTransaccionesVendedor_metodoSinCoincidencia_listaVacia() {
        when(transactionRepository.findByProducto_Vendedor_IdAndMetodoPago(3, PaymentMethod.BANCA))
                .thenReturn(Collections.emptyList());

        List<ShowTransactionDTO> result = sellerAuthTransactionServiceimpl
                .obtenerTransaccionesPorVendedorPorMetodoPago(3, PaymentMethod.BANCA);

        assertTrue(result.isEmpty());
    }

}
