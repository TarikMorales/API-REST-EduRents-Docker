package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;

public class SellerMapper {

    public static SellerDTO toDTO(Seller seller) {
        if (seller == null) return null;

        SellerDTO dto = new SellerDTO();
        dto.setId(seller.getId());
        dto.setResena(seller.getResena());
        dto.setConfiabilidad(seller.getConfiabilidad());
        dto.setSin_demoras(seller.getSin_demoras());
        dto.setBuena_atencion(seller.getBuena_atencion());

        if (seller.getUsuario() != null) {
            String nombres = seller.getUsuario().getNombres();
            String apellidos = seller.getUsuario().getApellidos();
            dto.setNombreUsuario((nombres != null ? nombres : "") + " " + (apellidos != null ? apellidos : ""));
        }

        return dto;
    }

    public static Seller toEntity(SellerDTO dto) {
        if (dto == null) return null;

        Seller seller = new Seller();
        seller.setId(dto.getId());
        seller.setResena(dto.getResena());
        seller.setConfiabilidad(dto.getConfiabilidad());
        seller.setSin_demoras(dto.getSin_demoras());
        seller.setBuena_atencion(dto.getBuena_atencion());
        return seller;
    }
}
