package com.ingsoft.tf.api_edurents.dto.exchanges;

import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ExchangeOfferDTO {
    private Integer id_usuario;
    private Integer id_producto;
    private String mensaje_propuesta;
    @Enumerated(EnumType.STRING)
    private ExchangeStatus estado;
}
