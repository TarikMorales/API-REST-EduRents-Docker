package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import com.ingsoft.tf.api_edurents.repository.exchanges.ExchangeOfferRepository;
import com.ingsoft.tf.api_edurents.service.exchanges.AdminExchangeOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exchanges")
public class ExchangeOfferController {
    private final AdminExchangeOfferService adminExchangeOfferService;
  
    @GetMapping("/seller/{sellerId}")
    public List<ExchangeOfferDTO> getOffersByUser(@PathVariable Integer sellerId) {
        return adminExchangeOfferService.getOffersByUser(sellerId);
    }
  
    @GetMapping("/user/{idUsuario}")
    public List<ExchangeOfferDTO> getOffersByUser(@PathVariable Integer idUsuario) {
        return adminExchangeOfferService.getOffersByUser(idUsuario);
    }
}

