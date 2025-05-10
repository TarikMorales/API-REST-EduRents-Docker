package com.ingsoft.tf.api_edurents.dto.transfers;

import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransactionDTO {

    @NotBlank(message = "Siempre debe haber un producto")
    private Integer id_producto;
    @NotBlank(message = "Siempre debe haber un usuario")
    private Integer id_usuario;

    @NotBlank(message = "Debes elegir un metodo de pago")
    @Enumerated(EnumType.STRING)
    private PaymentMethod metodo_pago;
}
