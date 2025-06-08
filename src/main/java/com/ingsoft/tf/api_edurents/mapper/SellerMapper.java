package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import org.springframework.stereotype.Component;

@Component
public class SellerMapper {

    public SellerDTO toSellerDTO(Seller seller) {
        SellerDTO dto = new SellerDTO();

        dto.setId(seller.getId());
        dto.setResena(seller.getResena());
        dto.setConfiabilidad(seller.getConfiabilidad());
        dto.setSin_demoras(seller.getSin_demoras());
        dto.setBuena_atencion(seller.getBuena_atencion());

        if (seller.getUsuario() != null) {
            String nombreCompleto = seller.getUsuario().getNombres() + " " + seller.getUsuario().getApellidos();
            dto.setNombreUsuario(nombreCompleto);
        } else {
            dto.setNombreUsuario("");
        }

        return dto;
    }

}
