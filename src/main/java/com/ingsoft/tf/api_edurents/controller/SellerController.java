package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.service.seller.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    // Ver confiabilidad del vendedor
    @GetMapping("/{id}/confiabilidad")
    public SellerDTO verConfiabilidad(@PathVariable Integer id) {
        return sellerService.getConfiabilidadById(id);
    }

    // Actualizar confiabilidad del vendedor
    @PutMapping("/{id}/confiabilidad")
    public SellerDTO actualizarConfiabilidad(@PathVariable Integer id, @RequestBody Boolean confiabilidad) {
        return sellerService.updateConfiabilidad(id, confiabilidad);
    }

}
