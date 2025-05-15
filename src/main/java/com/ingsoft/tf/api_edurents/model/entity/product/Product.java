package com.ingsoft.tf.api_edurents.model.entity.product;

import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import com.ingsoft.tf.api_edurents.model.entity.transfers.Transaction;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "productos")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;
    private Double precio;

    @Enumerated(EnumType.STRING)
    private ProductStatus estado;

    private LocalDate fecha_creacion;
    private LocalDate fecha_modificacion;
    private LocalDate fecha_expiracion;

    private Integer cantidad_disponible;
    private Boolean acepta_intercambio;

    @ManyToOne
    @JoinColumn(name = "id_vendedor",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_producto_categoria"), nullable = false)
    private Seller vendedor;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imagenes = new ArrayList<Image>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoriesProducts> categorias = new ArrayList<CategoriesProducts>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoursesCareersProduct> cursos_carreras = new ArrayList<CoursesCareersProduct>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transacciones = new ArrayList<Transaction>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExchangeOffer> intercambios = new ArrayList<ExchangeOffer>();
}