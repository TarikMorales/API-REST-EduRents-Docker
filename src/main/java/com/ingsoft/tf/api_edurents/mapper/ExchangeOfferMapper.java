package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;

import java.util.List;
import java.util.stream.Collectors;

public class ExchangeOfferMapper {
    public static ExchangeOfferDTO toDTO(ExchangeOffer eo) {
        ExchangeOfferDTO dto = new ExchangeOfferDTO();
        dto.setId(eo.getId());
        dto.setIdProducto(eo.getProducto().getId());
        dto.setMensajePropuesta(eo.getMensaje_propuesta());
        dto.setEstado(eo.getEstado());
        return dto;
    }
    public static List<ExchangeOfferDTO> toDTOs(List<ExchangeOffer> eos){
        return eos.stream().map(ExchangeOfferMapper::toDTO).collect(Collectors.toList());
    }
}
