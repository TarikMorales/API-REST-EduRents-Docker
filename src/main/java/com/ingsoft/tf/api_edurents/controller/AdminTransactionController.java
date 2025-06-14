package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ShowTransactionDTO crearTransaccion(@RequestBody @Valid TransactionDTO transaccionDTO) {
        return adminTransactionService.crearTransaccion(transaccionDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> cancelarTransaccion(@PathVariable Integer id){
        adminTransactionService.cancelarTransaccion(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Transacción con ID " + id + " cancelada exitosamente.");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowTransactionDTO> getTransaction(@PathVariable Integer id) {
        return ResponseEntity.ok(adminTransactionService.obtenerTransaccionPorId(id));
    }

    @GetMapping
    public List<ShowTransactionDTO> obtenerTransacciones() {
        return adminTransactionService.obtenerTransacciones();
    }

    @GetMapping("/{idTransaction}/user/{idUser}")
    public ResponseEntity<ShowTransactionDTO> obtenerPorIdPorUsuario(
            @PathVariable Integer idTransaction,
            @PathVariable Integer idUser) {
        return ResponseEntity.ok(adminTransactionService.obtenerTransaccionPorIdPorUsuario(idTransaction, idUser));
    }


}