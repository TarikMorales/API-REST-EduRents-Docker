package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
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
        if (transaccion.getUsuarios() != null) {
            UserDTO usuarioDTO = new UserDTO();
            usuarioDTO.setId(transaccion.getUsuarios().getId());
            usuarioDTO.setNombres(transaccion.getUsuarios().getNombres());
            usuarioDTO.setApellidos(transaccion.getUsuarios().getApellidos());
            usuarioDTO.setCorreo(transaccion.getUsuarios().getCorreo());
            usuarioDTO.setCodigo_universitario(transaccion.getUsuarios().getCodigo_universitario());
            usuarioDTO.setCiclo(transaccion.getUsuarios().getCiclo());
        }

        // Asignar Producto
        if (transaccion.getProductos() != null) {
            ProductDTO productoDTO = new ProductDTO();
            productoDTO.setId(transaccion.getProductos().getId());
            productoDTO.setNombre(transaccion.getProductos().getNombre());
            productoDTO.setDescripcion(transaccion.getProductos().getDescripcion());
            productoDTO.setPrecio(transaccion.getProductos().getPrecio());
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
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        transaccion.setUsuarios(usuario);


        // Asignacion Producto
        Product product = productRepository.findById(transaccionDTO.getId_producto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        transaccion.setProductos(product);

        // Guardamos la transaccion
        transactionRepository.save(transaccion);

        // Convertimos a DTO para devolver
        return convertShowTransactionDTO(transaccion);

    }


}
