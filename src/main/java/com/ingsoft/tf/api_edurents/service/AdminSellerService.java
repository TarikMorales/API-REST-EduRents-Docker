package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;

public interface AdminSellerService {
    SellerDTO getConfiabilidadById(Integer id);
    SellerDTO updateConfiabilidad(Integer id, Boolean nuevaConfiabilidad);
}
