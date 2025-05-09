package com.ingsoft.tf.api_edurents.service.exchanges;

import com.ingsoft.tf.api_edurents.dto.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminExchangeOfferService {
    List<ExchangeOfferDTO> getAll();
    Page<ExchangeOffer> paginate(Pageable pageable);
    ExchangeOffer create(ExchangeOffer exchangeOffer);
    ExchangeOffer update(Integer id, ExchangeOffer updateExchangeOffer);
    void delete(Integer id);
    List<ExchangeOfferDTO> getOffersByUser(Integer idUsuario);
    ExchangeOffer findById(Integer id);
}
