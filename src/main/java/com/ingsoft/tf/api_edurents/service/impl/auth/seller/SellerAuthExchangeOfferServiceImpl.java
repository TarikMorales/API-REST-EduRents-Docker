package com.ingsoft.tf.api_edurents.service.impl.auth.seller;

import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ExchangeOfferMapper;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeStatus;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.repository.exchanges.ExchangeOfferRepository;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.Interface.auth.seller.SellerAuthExchangeOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerAuthExchangeOfferServiceImpl implements SellerAuthExchangeOfferService {

    @Autowired
    private ExchangeOfferRepository exchangeOfferRepository;

    @Autowired
    private ExchangeOfferMapper exchangeOfferMapper;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ShowExchangeOfferDTO> obtenerIntercambiosPorProductoPorVendedor(Integer idProduct, Integer idSeller) {
        Product producto = productRepository.findById(idProduct)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + idProduct + " no encontrado"));

        // Validar que el producto pertenezca al vendedor
        if (!producto.getVendedor().getId().equals(idSeller)) {
            throw new BadRequestException("El producto no pertenece al vendedor con ID " + idSeller);
        }

        // Buscar las propuestas de intercambio asociadas al producto
        List<ExchangeOffer> intercambios = exchangeOfferRepository.findByProducto_Id(idProduct);

        return intercambios.stream()
                .map(exchangeOfferMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public void aceptarIntercambio(Integer idExchangeOffer, Integer idSeller, Boolean aceptar) {
        ExchangeOffer intercambio = exchangeOfferRepository.findById(idExchangeOffer)
                .orElseThrow(() -> new ResourceNotFoundException("Intercambio con ID " + idExchangeOffer + " no encontrado"));

        // Validar que el intercambio pertenezca al vendedor
        if (!intercambio.getProducto().getVendedor().getId().equals(idSeller)) {
            throw new BadRequestException("El intercambio no pertenece al vendedor con ID " + idSeller);
        }

        // Actualizar el estado del intercambio
        if (aceptar) {
            intercambio.setEstado(ExchangeStatus.ACEPTADO);
        } else {
            intercambio.setEstado(ExchangeStatus.RECHAZADO);
        }
        exchangeOfferRepository.save(intercambio);
    }


}
