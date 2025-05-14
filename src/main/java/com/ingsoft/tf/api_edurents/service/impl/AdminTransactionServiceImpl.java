package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.transfers.Transaction;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.transfers.TransactionRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.AdminTransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AdminTransactionServiceImpl implements AdminTransactionService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    private ShowTransactionDTO convertShowTransactionDTO(Transaction transaccion) {

        ShowTransactionDTO transaccionDTOMostrar = new ShowTransactionDTO();

        transaccionDTOMostrar.setId(transaccion.getId());
        transaccionDTOMostrar.setFecha_transaccion(transaccion.getFecha_transaccion());
        transaccionDTOMostrar.setEstado(transaccion.getEstado());
        transaccionDTOMostrar.setMetodo_pago(transaccion.getMetodo_pago());

        // Asignar Usuario
        if (transaccion.getUsuario() != null) {
            UserDTO usuarioDTO = new UserDTO();
            usuarioDTO.setId(transaccion.getUsuario().getId());
            usuarioDTO.setNombres(transaccion.getUsuario().getNombres());
            usuarioDTO.setApellidos(transaccion.getUsuario().getApellidos());
            usuarioDTO.setCorreo(transaccion.getUsuario().getCorreo());
            usuarioDTO.setCodigo_universitario(transaccion.getUsuario().getCodigo_universitario());
            usuarioDTO.setCiclo(transaccion.getUsuario().getCiclo());

            transaccionDTOMostrar.setUsuario(usuarioDTO);
        }

        // Asignar Producto
        if (transaccion.getProducto() != null) {
            ProductDTO productoDTO = new ProductDTO();
            productoDTO.setId(transaccion.getProducto().getId());
            productoDTO.setNombre(transaccion.getProducto().getNombre());
            productoDTO.setDescripcion(transaccion.getProducto().getDescripcion());
            productoDTO.setPrecio(transaccion.getProducto().getPrecio());

            transaccionDTOMostrar.setProducto(productoDTO);
        }

        return transaccionDTOMostrar;

    }

    @Transactional
    @Override
    public ShowTransactionDTO crearTransaccion(TransactionDTO transaccionDTO) {

        Transaction transaccion = new Transaction();

        transaccion.setMetodo_pago(transaccionDTO.getMetodo_pago());
        transaccion.setFecha_transaccion(LocalDate.now().atStartOfDay());
        transaccion.setEstado(TransactionStatus.PENDIENTE);

        // Asignacion Usuario
        User usuario = userRepository.findById(transaccionDTO.getId_usuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        transaccion.setUsuario(usuario);


        // Asignacion Producto
        Product product = productRepository.findById(transaccionDTO.getId_producto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        transaccion.setProducto(product);

        // Guardamos la transaccion
        transactionRepository.save(transaccion);

        // Convertimos a DTO para devolver
        return convertShowTransactionDTO(transaccion);

    }


}
