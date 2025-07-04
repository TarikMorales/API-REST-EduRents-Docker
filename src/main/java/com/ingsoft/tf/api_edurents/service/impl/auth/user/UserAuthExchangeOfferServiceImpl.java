package com.ingsoft.tf.api_edurents.service.impl.auth.user;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ExchangeOfferMapper;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeStatus;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.exchanges.ExchangeOfferRepository;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthExchangeOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthExchangeOfferServiceImpl implements UserAuthExchangeOfferService {

    @Autowired
    private ExchangeOfferRepository exchangeOfferRepository;

    @Autowired
    private ExchangeOfferMapper exchangeOfferMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // HU 02

    @Transactional
    @Override
    public ShowExchangeOfferDTO crearIntercambio(ExchangeOfferDTO intercambioDTO) {

        User usuario = userRepository.findById(intercambioDTO.getId_usuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + intercambioDTO.getId_usuario()));

        Product producto = productRepository.findById(intercambioDTO.getId_producto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id " + intercambioDTO.getId_producto()));

        // No se puede intercambiar el producto si es propio
        if (producto.getVendedor().getUsuario().getId().equals(usuario.getId())) {
            throw new BadRequestException("No puedes ofrecer intercambio sobre tu propio producto.");
        }

        // No se puede hacer más de un intercambio sobre el mismo producto
        boolean yaExisteIntercambio = exchangeOfferRepository
                .existsByUsuarioIdAndProductoId(intercambioDTO.getId_usuario(), intercambioDTO.getId_producto());

        if (yaExisteIntercambio) {
            throw new BadRequestException("Ya has enviado una oferta de intercambio para este producto.");
        }

        // Crear nuevo intercambio
        ExchangeOffer intercambio = new ExchangeOffer();
        intercambio = exchangeOfferMapper.toEntity(intercambio, intercambioDTO, "crear");
        intercambio.setProducto(producto);
        intercambio.setUsuario(usuario);

        // Guardar e ir a DTO
        ExchangeOffer guardado = exchangeOfferRepository.save(intercambio);
        return exchangeOfferMapper.toResponse(guardado);
    }



    @Transactional(readOnly = true)
    @Override
    public ShowExchangeOfferDTO obtenerIntercambioPorIdPorUsuarioId(Integer id, Integer idUser) {
        ExchangeOffer intercambio = exchangeOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intercambio no encontrado con ID: " + id));

        // Validación: el intercambio debe ser del usuario
        if (!intercambio.getUsuario().getId().equals(idUser)) {
            throw new ResourceNotFoundException("El intercambio no pertenece al usuario con ID " + idUser);
        }

        return exchangeOfferMapper.toResponse(intercambio);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowExchangeOfferDTO> obtenerIntercambiosPorUsuario(Integer id) {
        List<ExchangeOffer> intercambios = exchangeOfferRepository.findAllByUsuarioId(id);
        if (!intercambios.isEmpty()) {
            return intercambios.stream()
                    .map(exchangeOffer -> exchangeOfferMapper.toResponse(exchangeOffer))
                    .toList();
        } else {
            throw new ResourceNotFoundException("No se encontraron intercambios para el usuario con ID: " + id);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowExchangeOfferDTO> obtenerIntercambiosPorVendedor(Integer id) {
        List<ExchangeOffer> intercambios = exchangeOfferRepository.findAllByVendedorId(id);
        if (!intercambios.isEmpty()) {
            return intercambios.stream()
                    .map(exchangeOffer -> exchangeOfferMapper.toResponse(exchangeOffer))
                    .toList();
        } else {
            throw new ResourceNotFoundException("No se encontraron intercambios para el vendedor con ID: " + id);
        }
    }

    @Transactional
    @Override
    public ShowExchangeOfferDTO actualizarIntercambio(Integer id, ExchangeOfferDTO intercambioDTO) {
        ExchangeOffer intercambio = exchangeOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intercambio no encontrado con ID: " + id));
        // Validar que el estado del intercambio sea PENDIENTE antes de editar
        if (!intercambio.getEstado().equals(ExchangeStatus.PENDIENTE)) {
            throw new BadRequestException("El intercambio con ID " + id + " no se puede editar porque no está en estado PENDIENTE.");
        }
        intercambio = exchangeOfferMapper.toEntity(intercambio, intercambioDTO, "editar");
        return exchangeOfferMapper.toResponse(intercambio);
    }

    @Transactional
    @Override
    public void eliminarIntercambio(Integer id) {
        ExchangeOffer intercambio = exchangeOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intercambio no encontrado con ID: " + id));
        // Validar que el estado del intercambio sea PENDIENTE antes de eliminar
        if (!intercambio.getEstado().equals(ExchangeStatus.PENDIENTE)) {
            throw new BadRequestException("El intercambio con ID " + id + " no se puede eliminar porque ya ha sido procesado.");
        }
        exchangeOfferRepository.delete(intercambio);
    }

}
