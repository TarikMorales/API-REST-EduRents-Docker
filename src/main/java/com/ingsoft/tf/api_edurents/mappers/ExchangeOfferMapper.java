package com.ingsoft.tf.api_edurents.mappers;

import com.ingsoft.tf.api_edurents.dto.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;

import java.util.List;
import java.util.stream.Collectors;

public class ExchangeOfferMapper {
    public static ExchangeOfferDTO toDTO(ExchangeOffer e) {
        return new ExchangeOfferDTO(
                e.getUsuario().getId(),
                e.getProducto().getId(),
                e.getMensaje_propuesta(),
                e.getEstado()
        );
    }
    public static List<ExchangeOfferDTO> toDTOs(List<ExchangeOffer> e){
        return e.stream().map(ExchangeOfferMapper::toDTO).collect(Collectors.toList());
    }
}
