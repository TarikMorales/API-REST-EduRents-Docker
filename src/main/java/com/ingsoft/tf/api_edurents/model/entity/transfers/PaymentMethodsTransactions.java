package com.ingsoft.tf.api_edurents.model.entity.transfers;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "metodos_de_pago_por_transferencias")
public class PaymentMethodsTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_transaccion",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_transaccion_metodo-transaccion"), nullable = false)
    private Transaction transaccion;

    @ManyToOne
    @JoinColumn(name = "id_metodo",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_metodo_metodo-transaccion"), nullable = false)
    private PaymentMethod metodo;
}
