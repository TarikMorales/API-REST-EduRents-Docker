package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeStatus;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.exchanges.ExchangeOfferRepository;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.AdminExchangeOfferService;
import com.ingsoft.tf.api_edurents.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminExchangeOfferServiceImpl implements AdminExchangeOfferService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExchangeOfferRepository exchangeOfferRepository;

    @Autowired
    private AdminProductServiceImpl adminProductService;

    private ShowExchangeOfferDTO convertToShowExchangeOfferDTO(ExchangeOffer intercambio) {
        ShowExchangeOfferDTO intercambioDTOMostrar = new ShowExchangeOfferDTO();
        intercambioDTOMostrar.setId(intercambio.getId());

        UserDTO usuarioDTO = new UserDTO();
        usuarioDTO.setId(intercambio.getUsuario().getId());
        usuarioDTO.setNombres(intercambio.getUsuario().getNombres());
        usuarioDTO.setApellidos(intercambio.getUsuario().getApellidos());
        usuarioDTO.setCorreo(intercambio.getUsuario().getCorreo());
        usuarioDTO.setCodigo_universitario(intercambio.getUsuario().getCodigo_universitario());
        usuarioDTO.setCiclo(intercambio.getUsuario().getCiclo());
        usuarioDTO.setCarrera(intercambio.getUsuario().getCarrera().getNombre());
        intercambioDTOMostrar.setUsuario(usuarioDTO);

        ShowProductDTO productoDTO = adminProductService.convertToShowProductDTO(intercambio.getProducto());
        intercambioDTOMostrar.setProducto(productoDTO);

        intercambioDTOMostrar.setMensaje_propuesta(intercambio.getMensaje_propuesta());
        intercambioDTOMostrar.setEstado(intercambio.getEstado());

        return intercambioDTOMostrar;
    }

    private ExchangeOffer convertToExchangeOffer(ExchangeOffer intercambio, ExchangeOfferDTO intercambioDTO, String tipo) {
        intercambio.setMensaje_propuesta(intercambioDTO.getMensaje_propuesta());
        if (tipo.equals("crear")) {
            intercambio.setEstado(ExchangeStatus.PENDIENTE);
        } else if (tipo.equals("editar")) {
            intercambio.setEstado(intercambioDTO.getEstado());
        }

        Product producto = productRepository.findById(intercambioDTO.getId_producto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        intercambio.setProducto(producto);

        User usuario = userRepository.findById(intercambioDTO.getId_usuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        intercambio.setUsuario(usuario);

        exchangeOfferRepository.save(intercambio);

        return intercambio;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowExchangeOfferDTO> obtenerTodosLosIntercambios(){
        List<ExchangeOffer> intercambios = exchangeOfferRepository.findAll();
        return intercambios.stream()
                .map(this::convertToShowExchangeOfferDTO)
                .toList();
    }

    @Transactional
    @Override
    public ShowExchangeOfferDTO crearIntercambio(ExchangeOfferDTO intercambioDTO) {
        ExchangeOffer intercambio = new ExchangeOffer();
        intercambio = convertToExchangeOffer(intercambio, intercambioDTO, "crear");
        // Convertimos a DTO para devolver
        ShowExchangeOfferDTO intercambioDTOMostrar = convertToShowExchangeOfferDTO(intercambio);
        return intercambioDTOMostrar;
    }

}
