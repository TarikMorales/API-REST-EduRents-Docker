package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.AdminTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class AdminTransactionController {

    private final AdminTransactionService adminTransactionService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ShowTransactionDTO crearTransaccion(@RequestBody TransactionDTO transaccionDTO) {
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

}
