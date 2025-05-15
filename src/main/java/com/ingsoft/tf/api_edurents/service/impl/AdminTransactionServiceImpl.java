package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.transfers.transaction.Transaction;
import com.ingsoft.tf.api_edurents.model.entity.transfers.transaction.TransactionStatus;
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

    @Autowired
    private AdminProductServiceImpl adminProductService;

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
            ShowProductDTO productoDTO = adminProductService.convertToShowProductDTO(producto);

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
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        transaction.setUsuario(usuario);

        // Asignación Producto
        Product product = productRepository.findById(transaccionDTO.getId_producto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
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
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        List<Transaction> transacciones = transactionRepository.findByUsuario(usuario);

        return transacciones.stream()
                .map(this::convertShowTransactionDTO)
                .collect(Collectors.toList());
    }

    @Transactional()
    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorEstado(Integer idUsuario, TransactionStatus estado) {
        User usuario = userRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        List<Transaction> transacciones = transactionRepository.findByUsuarioAndEstado(usuario, estado);

        return transacciones.stream()
                .map(this::convertShowTransactionDTO)
                .collect(Collectors.toList());
    }

    @Transactional()
    @Override
    public ShowTransactionDTO confirmarEntregaPago(Integer idTransaccion, TransactionStatus nuevoEstado) {
        Transaction transaccion = transactionRepository.findById(idTransaccion)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada con id: " + idTransaccion));

        transaccion.setEstado(nuevoEstado);
        transactionRepository.save(transaccion);

        // Retornar DTO completo
        return convertShowTransactionDTO(transaccion);
    }
  
    @Transactional()
    @Override
    public void cancelarTransaccion(Integer id){
        Transaction transaccion = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaccion no encontrada con id: " + id));
        transactionRepository.delete(transaccion);
    }

}
