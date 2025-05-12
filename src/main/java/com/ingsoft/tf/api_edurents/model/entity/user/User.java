package com.ingsoft.tf.api_edurents.model.entity.user;

import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import com.ingsoft.tf.api_edurents.model.entity.university.Career;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombres;
    private String apellidos;
    private String correo;
    private String contrasena;
    private String codigo_universitario;
    private Byte ciclo;
    private String foto_url;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Seller vendedor;

    @ManyToOne
    @JoinColumn(name = "id_carrera",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario_carrera"), nullable = false)
    private Career carrera;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExchangeOffer> intercambios = new ArrayList<ExchangeOffer>();

}
