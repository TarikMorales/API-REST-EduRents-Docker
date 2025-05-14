package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;

import java.util.List;

public interface AdminExchangeOfferService {
    List<ShowExchangeOfferDTO> obtenerTodosLosIntercambios();
    ShowExchangeOfferDTO crearIntercambio(ExchangeOfferDTO intercambioDTO);
}
