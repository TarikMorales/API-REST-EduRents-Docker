package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.transfers.ClaimTransactionDTO;
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

    //HU 14

    @PutMapping("/{id}/confirm")
    public ResponseEntity<Map<String, Object>> confirmarEntregaPago(@PathVariable Integer id) {
        ShowTransactionDTO dto = adminTransactionService.confirmarEntregaPago(id); // ya no se pasa el estado desde aquí

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Entrega de la transacción " + dto.getId() + " confirmada exitosamente.");
        response.put("transaccion", dto);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}/claim")
    public ResponseEntity<Map<String, Object>> reclamarTransaccion(
            @PathVariable Integer id,
            @Valid @RequestBody ClaimTransactionDTO dto
    ) {
        ShowTransactionDTO updated = adminTransactionService.reclamarTransaccion(id, dto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Reclamo registrado exitosamente en la transacción " + updated.getId());
        response.put("transaccion", updated);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{idUsuario}/product/{idProducto}")
    public ResponseEntity<ShowTransactionDTO> obtenerPorProductoYUsuario(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idProducto) {
        ShowTransactionDTO dto = adminTransactionService.obtenerTransaccionPorProductoYUsuario(idProducto, idUsuario);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/seller/{idVendedor}/product/{idProducto}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorProductoYVendedor(
            @PathVariable Integer idVendedor,
            @PathVariable Integer idProducto) {
        return ResponseEntity.ok(adminTransactionService.obtenerTransaccionesPorProductoYVendedor(idProducto, idVendedor));
    }

}