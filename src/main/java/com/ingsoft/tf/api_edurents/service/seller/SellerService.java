package com.ingsoft.tf.api_edurents.service.seller;

import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;

public interface SellerService {
    SellerDTO getConfiabilidadById(Integer id);
    SellerDTO updateConfiabilidad(Integer id, Boolean nuevaConfiabilidad);
}
