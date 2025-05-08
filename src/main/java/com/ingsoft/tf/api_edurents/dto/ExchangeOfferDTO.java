package com.ingsoft.tf.api_edurents.dto;

import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeStatus;

public class ExchangeOfferDTO {
    private Integer idUsuario;
    private Integer idProducto;
    private String mensajePropuesta;
    private ExchangeStatus estado;

    // Constructor
    public ExchangeOfferDTO(Integer idUsuario, Integer idProducto, String mensajePropuesta, ExchangeStatus estado) {
        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
        this.mensajePropuesta = mensajePropuesta;
        this.estado = estado;
    }

    // Getters
    public Integer getIdUsuario() { return idUsuario; }
    public Integer getIdProducto() { return idProducto; }
    public String getMensajePropuesta() { return mensajePropuesta; }
    public ExchangeStatus getEstado() { return estado; }
}
