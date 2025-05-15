package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.mapper.SellerMapper;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
import com.ingsoft.tf.api_edurents.service.seller.SellerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public SellerDTO getConfiabilidadById(Integer id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor no encontrado con ID: " + id));
        return SellerMapper.toDTO(seller);
    }

    @Override
    public SellerDTO updateConfiabilidad(Integer id, Boolean nuevaConfiabilidad) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor no encontrado con ID: " + id));

        seller.setConfiabilidad(nuevaConfiabilidad);
        seller = sellerRepository.save(seller);

        return SellerMapper.toDTO(seller);
    }
}
