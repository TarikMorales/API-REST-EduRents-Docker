package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;
    private final SellerMapper sellerMapper;
    private final ImageMapper imageMapper;
    private final CourseCareerProductMapper coursesCareersProductMapper;
    private final CategoriesProductsMapper categoriesProductsMapper;

    public ProductMapper(
            CategoryMapper categoryMapper,
            SellerMapper sellerMapper,
            ImageMapper imageMapper,
            CourseCareerProductMapper coursesCareersProductMapper,
            CategoriesProductsMapper categoriesProductsMapper
    ) {
        this.categoryMapper = categoryMapper;
        this.sellerMapper = sellerMapper;
        this.imageMapper = imageMapper;
        this.coursesCareersProductMapper = coursesCareersProductMapper;
        this.categoriesProductsMapper = categoriesProductsMapper;
    }

    public Product toEntity(ProductDTO request) {
        Product product = new Product();
        product.setNombre(request.getNombre());
        product.setDescripcion(request.getDescripcion());
        product.setPrecio(request.getPrecio());
        product.setEstado(request.getEstado());
        product.setCantidad_disponible(request.getCantidad_disponible());
        product.setAcepta_intercambio(request.getAcepta_intercambio());
        product.setFecha_expiracion(request.getFecha_expiracion());
        return product;
    }

    public ShowProductDTO toResponse(Product product) {
        ShowProductDTO dto = new ShowProductDTO();

        dto.setId(product.getId());
        dto.setNombre(product.getNombre());
        dto.setDescripcion(product.getDescripcion());
        dto.setPrecio(product.getPrecio());
        dto.setEstado(product.getEstado());
        dto.setCantidad_disponible(product.getCantidad_disponible());
        dto.setAcepta_intercambio(product.getAcepta_intercambio());
        dto.setFecha_creacion(product.getFecha_creacion());
        dto.setFecha_modificacion(product.getFecha_modificacion());
        dto.setFecha_expiracion(product.getFecha_expiracion());
        dto.setVistas(product.getVistas());

        // Vendedor
        dto.setVendedor(sellerMapper.toSellerDTO(product.getVendedor()));

        // Imagenes
        dto.setImagenes(product.getImagenes().stream()
                .map(imageMapper::toImageDTO)
                .toList());

        // Categorias
        dto.setCategorias(product.getCategorias().stream()
                .map(categoriesProductsMapper::toCategoryDTO)
                .toList());

        // CursosCarreras
        dto.setCursos_carreras(product.getCursos_carreras().stream()
                .map(coursesCareersProductMapper::toResponse)
                .toList());

        return dto;
    }

}
