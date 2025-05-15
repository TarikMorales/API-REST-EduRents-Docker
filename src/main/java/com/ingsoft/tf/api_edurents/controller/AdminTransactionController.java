package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.transaction.TransactionStatus;
import com.ingsoft.tf.api_edurents.service.AdminTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class AdminTransactionController {

    private final AdminTransactionService adminTransactionService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ShowTransactionDTO crearTransaccion(@RequestBody @Valid TransactionDTO transaccionDTO) {
        return adminTransactionService.crearTransaccion(transaccionDTO);
    }

    @GetMapping
    public List<ShowTransactionDTO> obtenerTransacciones() {
        return adminTransactionService.obtenerTransacciones();
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<ShowTransactionDTO> obtenerTransaccionesPorUsuario(@PathVariable Integer idUsuario) {
        return adminTransactionService.obtenerTransaccionesPorUsuario(idUsuario);
    }

    @GetMapping("/usuario/{idUsuario}/estado/{estado}")
    public List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorEstado(
            @PathVariable Integer idUsuario,
            @PathVariable TransactionStatus estado) {
        return adminTransactionService.obtenerTransaccionesPorUsuarioPorEstado(idUsuario, estado);
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<ShowTransactionDTO> confirmarEntregaPago(@PathVariable Integer id) {
        ShowTransactionDTO updated = adminTransactionService.confirmarEntregaPago(id, TransactionStatus.PAGADO);
        return ResponseEntity.ok(updated);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void cancelarTransaccion(@PathVariable Integer id){
        adminTransactionService.cancelarTransaccion(id);
    }

}
