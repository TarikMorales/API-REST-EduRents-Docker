package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.service.AdminExchangeOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exchanges")
public class AdminExchangeOfferController {

    private final AdminExchangeOfferService adminExchangeOfferService;

    @GetMapping
    public List<ShowExchangeOfferDTO> obtenerIntercambios(){
        return adminExchangeOfferService.obtenerTodosLosIntercambios();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ShowExchangeOfferDTO crearIntercambio(@RequestBody ExchangeOfferDTO intercambioDTO){
        return adminExchangeOfferService.crearIntercambio(intercambioDTO);
    }

}
