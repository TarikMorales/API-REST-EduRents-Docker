package com.ingsoft.tf.api_edurents.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    private Integer id;
    private Integer cantidad_disponible;
}