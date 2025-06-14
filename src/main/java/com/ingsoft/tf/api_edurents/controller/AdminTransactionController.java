package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.service.AdminTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class AdminTransactionController {

    private final AdminTransactionService adminTransactionService;

    // Historial como usuario (comprador)
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorUsuario(@PathVariable Integer idUser) {
        return ResponseEntity.ok(adminTransactionService.obtenerTransaccionesPorUsuario(idUser));
    }

    @GetMapping("/user/{idUser}/state/{estado}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorUsuarioYEstado(
            @PathVariable Integer idUser,
            @PathVariable TransactionStatus estado) {
        return ResponseEntity.ok(adminTransactionService.obtenerTransaccionesPorUsuarioPorEstado(idUser, estado));
    }

    @GetMapping("/user/{idUser}/paymentMethod/{metodo}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorUsuarioYMetodoPago(
            @PathVariable Integer idUser,
            @PathVariable PaymentMethod metodo) {
        return ResponseEntity.ok(adminTransactionService.obtenerTransaccionesPorUsuarioPorMetodoPago(idUser, metodo));
    }

    @GetMapping("/user/{idUser}/paymentMethod/{metodo}/state/{estado}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorUsuarioMetodoPagoYEstado(
            @PathVariable Integer idUser,
            @PathVariable PaymentMethod metodo,
            @PathVariable TransactionStatus estado) {
        return ResponseEntity.ok(adminTransactionService.obtenerTransaccionesPorUsuarioPorMetodoPagoPorEstado(idUser, metodo, estado));
    }

    // Historial como vendedor
    @GetMapping("/seller/{idSeller}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorVendedor(@PathVariable Integer idSeller) {
        return ResponseEntity.ok(adminTransactionService.obtenerTransaccionesPorVendedor(idSeller));
    }

    @GetMapping("/seller/{idSeller}/state/{estado}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorVendedorYEstado(
            @PathVariable Integer idSeller,
            @PathVariable TransactionStatus estado) {
        return ResponseEntity.ok(adminTransactionService.obtenerTransaccionesPorVendedorPorEstado(idSeller, estado));
    }

    @GetMapping("/seller/{idSeller}/paymentMethod/{metodo}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorVendedorYMetodoPago(
            @PathVariable Integer idSeller,
            @PathVariable PaymentMethod metodo) {
        return ResponseEntity.ok(adminTransactionService.obtenerTransaccionesPorVendedorPorMetodoPago(idSeller, metodo));
    }

    @GetMapping("/seller/{idSeller}/paymentMethod/{metodo}/state/{estado}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorVendedorMetodoPagoYEstado(
            @PathVariable Integer idSeller,
            @PathVariable PaymentMethod metodo,
            @PathVariable TransactionStatus estado) {
        return ResponseEntity.ok(adminTransactionService.obtenerTransaccionesPorVendedorPorMetodoPagoPorEstado(idSeller, metodo, estado));
    }


}
