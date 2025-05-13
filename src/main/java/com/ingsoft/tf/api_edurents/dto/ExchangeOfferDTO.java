package com.ingsoft.tf.api_edurents.dto;

import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeStatus;
import lombok.Data;

@Data
public class ExchangeOfferDTO {
    private Integer id;
    private Integer idProducto;
    private String mensajePropuesta;
    private ExchangeStatus estado;
}
