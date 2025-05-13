package com.ingsoft.tf.api_edurents.model.entity.transfers;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "metodos_de_pago")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;

    @OneToMany(mappedBy = "metodo_pago", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentMethodsTransactions> transacciones = new ArrayList<PaymentMethodsTransactions>();

}
