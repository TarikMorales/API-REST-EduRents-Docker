package com.ingsoft.tf.api_edurents.dto.exchanges;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import lombok.Data;

@Data
public class ShowExchangeOfferDTO {
    private Integer id;
    private UserDTO usuario;
    private ShowProductDTO producto;
    private String mensaje_propuesta;
    private String estado;
}
