package com.ingsoft.tf.api_edurents.dto.transfers;

import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowTransactionDTO {
    private Integer id;
    private Integer id_producto;
    private Integer id_usuario;

    @Enumerated(EnumType.STRING)
    private PaymentMethod metodo_pago;

    private LocalDateTime fecha_transaccion;

    @Enumerated(EnumType.STRING)
    private TransactionStatus estado;
}
