package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.service.AdminTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
