package com.ingsoft.tf.api_edurents.dto.transfers;

import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransactionDTO {

    private Integer id_producto;
    private Integer id_usuario;

    @NotBlank(message = "Debes elegir un metodo de pago")
    @Enumerated(EnumType.STRING)
    private PaymentMethod metodo_pago;

    @Enumerated(EnumType.STRING)
    private TransactionStatus estado;
}
