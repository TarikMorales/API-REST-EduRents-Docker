package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.mapper.ExchangeOfferMapper;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import com.ingsoft.tf.api_edurents.repository.exchanges.ExchangeOfferRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.exchanges.AdminExchangeOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminExchangeOfferServiceImpl implements AdminExchangeOfferService {
    @Autowired
    private ExchangeOfferRepository exchangeOfferRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ExchangeOfferDTO> getOffersByUser(Integer idUsuario){
        userRepository.findById(idUsuario)
                .orElseThrow(()->new RuntimeException("User ID not found"));
        return ExchangeOfferMapper.toDTOs(exchangeOfferRepository.findAllByUsuario_Id(idUsuario));
    }

    @Override
    public ExchangeOffer findById(Integer id) {
        return null;
    }

    @Override
    public List<ExchangeOffer> getAll() {
        return List.of();
    }

    @Override
    public Page<ExchangeOffer> paginate(Pageable pageable) {
        return null;
    }

    @Override
    public ExchangeOffer create(ExchangeOffer exchangeOffer) {
        return null;
    }

    @Override
    public ExchangeOffer update(Integer id, ExchangeOffer updateExchangeOffer) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
