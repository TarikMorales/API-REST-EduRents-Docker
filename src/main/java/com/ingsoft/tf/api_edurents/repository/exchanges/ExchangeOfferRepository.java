package com.ingsoft.tf.api_edurents.repository.exchanges;

import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangeOfferRepository extends JpaRepository<ExchangeOffer, Integer> {
    List<ExchangeOffer> findAllByUsuarioId(Integer idUsuario);
}
