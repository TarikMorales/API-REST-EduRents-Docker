package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeStatus;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import org.springframework.stereotype.Component;
import com.ingsoft.tf.api_edurents.repository.exchanges.ExchangeOfferRepository;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;


@Component
public class ExchangeOfferMapper {
    public ExchangeOffer toEntity(ExchangeOfferDTO dto, User user, Product product) {
        ExchangeOffer offer = new ExchangeOffer();
        offer.setUsuario(user);
        offer.setProducto(product);
        offer.setMensaje_propuesta(dto.getMensaje_propuesta());

        if (dto.getEstado() != null) {
            offer.setEstado(dto.getEstado());
        } else {
            offer.setEstado(ExchangeStatus.PENDIENTE);
        }

        return offer;
    }

    public ShowExchangeOfferDTO toResponse(ExchangeOffer offer, UserDTO userDTO, ShowProductDTO showProductDTO) {
        ShowExchangeOfferDTO dto = new ShowExchangeOfferDTO();
        dto.setId(offer.getId());
        dto.setUsuario(userDTO);
        dto.setProducto(showProductDTO);
        dto.setMensaje_propuesta(offer.getMensaje_propuesta());
        dto.setEstado(offer.getEstado());
        return dto;
    }
}
