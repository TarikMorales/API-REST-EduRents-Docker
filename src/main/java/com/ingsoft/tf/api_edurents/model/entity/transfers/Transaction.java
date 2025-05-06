package com.ingsoft.tf.api_edurents.model.entity.transfers;

import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "transacciones")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TransactionStatus estado;
    private LocalDateTime fecha_transaccion;

    @ManyToOne
    @JoinColumn(name = "id_producto",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_producto_transaccion"), nullable = false)
    private Product productos;

    @ManyToOne
    @JoinColumn(name = "id_usuario",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario_transaccion"), nullable = false)
    private User usuarios;

    @OneToMany(mappedBy = "transaccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentMethodsTransactions> metodos_pago = new ArrayList<PaymentMethodsTransactions>();

}
