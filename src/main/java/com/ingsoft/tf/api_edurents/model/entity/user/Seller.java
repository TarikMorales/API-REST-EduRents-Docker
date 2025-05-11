package com.ingsoft.tf.api_edurents.model.entity.user;

import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "vendedores")
public class Seller {
    @Id
    private Integer id;

    private String resena;
    private Boolean confiabilidad;
    private Boolean sin_demoras;
    private Boolean buena_atencion;

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productos = new ArrayList<Product>();

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario_vendedor"))
    private User usuario;
}
