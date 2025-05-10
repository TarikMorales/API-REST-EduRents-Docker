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
import java.util.List;
import java.util.stream.Collectors;

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
            Product producto = transaccion.getProducto();
            ProductDTO productoDTO = new ProductDTO();

            productoDTO.setId(producto.getId());
            productoDTO.setNombre(producto.getNombre());
            productoDTO.setDescripcion(producto.getDescripcion());
            productoDTO.setPrecio(producto.getPrecio());
            productoDTO.setEstado(producto.getEstado());
            productoDTO.setCantidad_disponible(producto.getCantidad_disponible());
            productoDTO.setAcepta_intercambio(producto.getAcepta_intercambio());

            // id_vendedor
            if (producto.getVendedor() != null) {
                productoDTO.setId_vendedor(producto.getVendedor().getId());
            }

            // urls_imagenes
            if (producto.getImagenes() != null) {
                List<String> urls = producto.getImagenes().stream()
                        .map(imagen -> imagen.getUrl()) // Suponiendo que `Image` tiene un campo `url`
                        .collect(Collectors.toList());
                productoDTO.setUrls_imagenes(urls);
            }

            // categorias
            if (producto.getCategorias() != null) {
                List<Integer> categorias = producto.getCategorias().stream()
                        .map(catProd -> catProd.getCategoria().getId()) // Suponiendo que `CategoriesProducts` tiene `getCategoria().getId()`
                        .collect(Collectors.toList());
                productoDTO.setCategorias(categorias);
            }

            // cursos_carreras
            if (producto.getCursos_carreras() != null) {
                List<Integer> cursos = producto.getCursos_carreras().stream()
                        .map(ccp -> ccp.getCurso_carrera().getId()) // Suponiendo que `CoursesCareersProduct` tiene `getCurso_carrera().getId()`
                        .collect(Collectors.toList());
                productoDTO.setCursos_carreras(cursos);
            }

            transaccionDTOMostrar.setProducto(productoDTO);
        }

        return transaccionDTOMostrar;

    }

    private Transaction convertToTransaction(Transaction transaction, TransactionDTO transaccionDTO, String tipo) {
        transaction.setFecha_transaccion(LocalDate.now().atStartOfDay());
        transaction.setMetodo_pago(transaccionDTO.getMetodo_pago());

        if (tipo.equals("crear")) {
            transaction.setEstado(TransactionStatus.PENDIENTE);
        } else {
            transaction.setEstado(TransactionStatus.PAGADO);
        }

        // Asignación Usuario
        User usuario = userRepository.findById(transaccionDTO.getId_usuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        transaction.setUsuario(usuario);

        // Asignación Producto
        Product product = productRepository.findById(transaccionDTO.getId_producto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        transaction.setProducto(product);

        // Guardamos y retornamos
        return transactionRepository.save(transaction);
    }


    @Transactional
    @Override
    public ShowTransactionDTO crearTransaccion(TransactionDTO transaccionDTO) {

        Transaction transaccion = new Transaction();

        // Convertimos DTO a entidad
        transaccion = convertToTransaction(transaccion, transaccionDTO, "crear");

        // Convertimos a DTO para devolver
        return convertShowTransactionDTO(transaccion);

    }

    @Transactional()
    @Override
    public List<ShowTransactionDTO> obtenerTransacciones() {
        List<Transaction> transacciones = transactionRepository.findAll();
        return transacciones.stream()
                .map(this::convertShowTransactionDTO)
                .collect(Collectors.toList());
    }

    @Transactional()
    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorUsuario(Integer idUsuario) {
        User usuario = userRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Transaction> transacciones = transactionRepository.findByUsuario(usuario);

        return transacciones.stream()
                .map(this::convertShowTransactionDTO)
                .collect(Collectors.toList());
    }

    @Transactional()
    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorEstado(Integer idUsuario, TransactionStatus estado) {
        User usuario = userRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Transaction> transacciones = transactionRepository.findByUsuarioAndEstado(usuario, estado);

        return transacciones.stream()
                .map(this::convertShowTransactionDTO)
                .collect(Collectors.toList());
    }

}
