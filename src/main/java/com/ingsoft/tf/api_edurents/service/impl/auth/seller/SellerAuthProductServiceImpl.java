package com.ingsoft.tf.api_edurents.service.impl.auth.seller;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Alert;
import com.ingsoft.tf.api_edurents.model.entity.product.AlertType;
import com.ingsoft.tf.api_edurents.model.entity.product.FollowedProduct;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.repository.product.AlertRepository;
import com.ingsoft.tf.api_edurents.repository.product.FollowedProductRepository;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.Interface.auth.seller.SellerAuthProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SellerAuthProductServiceImpl implements SellerAuthProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private FollowedProductRepository followedProductRepository;

    // Agregamos el mapper de Product
    @Autowired
    private ProductMapper productMapper;

    // HU01

    @Transactional
    @Override
    public ShowProductDTO crearProducto(ProductDTO productoDTO) {

        Product producto = new Product();
        // Convertimos el DTO a entidad
        producto = productMapper.toEntity(producto, productoDTO, "crear");

        // Guardar en base de datos
        producto = productRepository.save(producto);

        return productMapper.toResponse(producto);
    }

    public void notificarFollowersAboutChange(Product oldProduct, Product updatedProduct) {
        List<AlertType> alertTypes = new ArrayList<>();

        if (!Objects.equals(oldProduct.getPrecio(), updatedProduct.getPrecio())) {
            alertTypes.add(AlertType.CAMBIO_PRECIO);
        }

        if (!Objects.equals(oldProduct.getEstado(), updatedProduct.getEstado())) {
            alertTypes.add(AlertType.CAMBIO_ESTADO);
        }

        if (!Objects.equals(oldProduct.getCantidad_disponible(), updatedProduct.getCantidad_disponible())) {
            alertTypes.add(AlertType.CAMBIO_STOCK);
        }

        if (!Objects.equals(oldProduct.getFecha_expiracion(), updatedProduct.getFecha_expiracion())) {
            alertTypes.add(AlertType.NUEVA_FECHA_EXPIRACION);
        }

        if (!Objects.equals(oldProduct.getNombre(), updatedProduct.getNombre())) {
            alertTypes.add(AlertType.PRODUCTO_REPUBLICADO);
        }

        if (!Objects.equals(oldProduct.getDescripcion(), updatedProduct.getDescripcion())) {
            alertTypes.add(AlertType.PRODUCTO_REPUBLICADO);
        }

        if (!alertTypes.isEmpty()) {
            List<FollowedProduct> seguidores = followedProductRepository.findByProductoId(oldProduct.getId());

            for (AlertType tipo : alertTypes) {
                for (FollowedProduct fp : seguidores) {
                    Alert alert = new Alert();
                    alert.setTipo(tipo);
                    alert.setUsuario(fp.getUsuario());
                    alert.setProducto(oldProduct);
                    alert.setMensaje("El producto '" + oldProduct.getNombre() + "' ha tenido un " + tipo.name().toLowerCase().replace("_", " "));
                    alert.setFecha_creacion(LocalDateTime.now());
                    alert.setVisto(false);
                    alertRepository.save(alert);
                }
            }
        }

    }

    @Transactional
    @Override
    public ShowProductDTO editarProducto(Integer id, ProductDTO productoDTO) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        // Guardar una copia del producto antes de modificarlo
        Product productoAntes = new Product();
        productoAntes.setId(producto.getId());
        productoAntes.setNombre(producto.getNombre());
        productoAntes.setDescripcion(producto.getDescripcion());
        productoAntes.setPrecio(producto.getPrecio());
        productoAntes.setCantidad_disponible(producto.getCantidad_disponible());
        productoAntes.setFecha_expiracion(producto.getFecha_expiracion());
        productoAntes.setEstado(producto.getEstado());

        // Convertimos el DTO a entidad
        producto = productMapper.toEntity(producto, productoDTO, "editar");
        // Convertimos a DTO para devolver
        ShowProductDTO productoDTOMostrar = productMapper.toResponse(producto);

        // Notificar si hubo cambios
        notificarFollowersAboutChange(productoAntes, producto);

        return productoDTOMostrar;
    }

    public void notificarFollowersAboutDelete(Product producto, AlertType tipo) {
        List<FollowedProduct> seguidores = followedProductRepository.findByProductoId(producto.getId());

        for (FollowedProduct fp : seguidores) {
            Alert alert = new Alert();
            alert.setTipo(tipo);
            alert.setUsuario(fp.getUsuario());
            alert.setProducto(producto);
            alert.setMensaje("El producto '" + producto.getNombre() + "' ha sido " + tipo.name().toLowerCase().replace("_", " "));
            alert.setFecha_creacion(LocalDateTime.now());
            alert.setVisto(false);
            alertRepository.save(alert);
        }
    }

    @Transactional
    @Override
    public void eliminarProducto(Integer id) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        // Notificar a seguidores ANTES de eliminar
        notificarFollowersAboutDelete(producto, AlertType.PRODUCTO_ELIMINADO);

        productRepository.delete(producto);
    }

    //HU10

    @Transactional
    @Override
    public ShowProductDTO actualizarCantidadDisponible(Integer idProducto, Integer nuevaCantidad) {
        if (nuevaCantidad < 0) {
            throw new BadRequestException("La cantidad no puede ser negativa");
        }
        Product producto = productRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        producto.setCantidad_disponible(nuevaCantidad);
        productRepository.save(producto);
        ShowProductDTO productoDTOMostrar = productMapper.toResponse(producto);
        return productoDTOMostrar;
    }

    @Override
    public ShowProductDTO actualizarFechaExpiracion(Integer id, LocalDate fechaExpiracion) {
        // Obtener el producto por ID
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        // Actualizar la fecha de expiración
        producto.setFecha_expiracion(fechaExpiracion);

        // Guardar el producto actualizado
        productRepository.save(producto);

        // Crear y devolver el DTO con la nueva fecha de expiración
        ShowProductDTO dto = new ShowProductDTO();
        dto.setFecha_expiracion(producto.getFecha_expiracion());

        return dto;
    }

}
