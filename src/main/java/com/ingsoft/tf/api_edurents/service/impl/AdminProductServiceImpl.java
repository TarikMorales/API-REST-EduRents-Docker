package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.product.*;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.*;
import com.ingsoft.tf.api_edurents.model.entity.university.CoursesCareers;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.repository.product.*;
import com.ingsoft.tf.api_edurents.repository.university.CoursesCareersRepository;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
import com.ingsoft.tf.api_edurents.service.AdminProductService;
import io.micrometer.observation.annotation.Observed;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    @Autowired
    private ProductRepository productRepository;

    // Agregamos el mapper de Product
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CoursesCareersRepository coursesCareersRepository;

    @Autowired
    private CategoriesProductsRepository categoriesProductsRepository;

    @Autowired
    private CoursesCareersProductRepository coursesCareersProductRepository;

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ConversionService conversionService;

    private ProductMapper productMapper;

    private StockDTO convertToProductStockDTO(Product producto) {
        StockDTO dto = new StockDTO();
        dto.setId(producto.getId());
        dto.setCantidad_disponible(producto.getCantidad_disponible());
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerTodosLosProductos() {
        List<Product> productos = productRepository.findAll();
        return productos.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ShowProductDTO crearProducto(ProductDTO productoDTO) {

        Product producto = new Product();
        // Convertimos el DTO a entidad
        producto = productMapper.toEntity(producto, productoDTO, "crear");

        // Convertimos a DTO para devolver
        ShowProductDTO productoDTOMostrar = productMapper.toResponse(producto);
        return productoDTOMostrar;
    }

    @Transactional(readOnly = true)
    @Override
    public ShowProductDTO obtenerProductoPorId(Integer id) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return productMapper.toResponse(producto);
    }

    @Transactional(readOnly = true)
    @Override
    public StockDTO obtenerStockProductoPorId(Integer idProducto) {
        Product producto = productRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + idProducto));
        return convertToProductStockDTO(producto);
    }
    
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
    public ShowProductDTO obtenerFechaExpiracion(Integer id) {
        // Obtener el producto de la base de datos
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        // Verifica si la fecha de expiración es null en la entidad
        System.out.println("Fecha Expiración en el Producto: " + producto.getFecha_expiracion());

        // Crear un DTO para devolver solo la información necesaria
        ShowProductDTO dto = new ShowProductDTO();
        dto.setFecha_expiracion(producto.getFecha_expiracion());

        return dto;
    }

    @Transactional
    @Override
    public ShowProductDTO editarProducto(Integer id, ProductDTO productoDTO) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        // Convertimos el DTO a entidad
        producto = productMapper.toEntity(producto, productoDTO, "editar");
        // Convertimos a DTO para devolver
        ShowProductDTO productoDTOMostrar = productMapper.toResponse(producto);
        return productoDTOMostrar;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCursoYCarrera(Integer idCarrera, Integer idCurso) {
        List<Product> productos = productRepository.findByCareerAndCourse(idCarrera, idCurso);
        if (productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la carrera con id: " + idCarrera + " y curso con id: " + idCurso);
        }
        return productos.stream()
                .map(product -> productMapper.toResponse(product))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCategoria(Integer idCategoria) {
        List<Product> productos = productRepository.findByCategoryId(idCategoria);
        if (productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la categoría con id: " + idCategoria);
        }
        return productos.stream()
                .map(product -> productMapper.toResponse(product))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void eliminarProducto(Integer id) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        productRepository.delete(producto);
    }

    @Transactional
    @Override
    public List<ShowProductDTO> obtenerTop10ProductosPorVistas() {
        Pageable top10 = PageRequest.of(0, 10);
        List<Product> products = productRepository.findAllByOrderByVistasDesc(top10);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el top 10 en base a vistas");
    public void sumarView(Integer id){
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        producto.setVistas(producto.getVistas() + 1);
        productRepository.save(producto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosRecomendados(Integer idCareer){
        List<Product> products = productRepository.findByCareerOrderByViews(idCareer);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraro productos recomendados para la carrera: " + idCareer);
        }
        return products.stream()
                .map(this::convertToShowProductDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ShowProductDTO> obtenerTop10ProductosPorCantidadDeIntercambios() {
        Pageable top10 = PageRequest.of(0, 10);
        List<Product> products = productRepository.findTopProductsByExchangeOfferCount(top10);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el top 10 em base a cantidad de intercambios");
    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCarreraOrdenarPorVistas(Integer idCareer){
        List<Product> products = productRepository.findByCareerOrderByViews(idCareer);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la carrera con id: " + idCareer);
        }
        return products.stream()
                .map(this::convertToShowProductDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ShowProductDTO> obtener10ProductosRecientes(){
        Pageable top10 = PageRequest.of(0, 10);
        List<Product> products = productRepository.findAllByOrderByFecha_creacionDesc(top10);
        if(products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos recientes");
        }
        return products.stream()
    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCursoOrdenarPorVistas(Integer idCourse){
        List<Product> products = productRepository.findByCourseOrderByViews(idCourse);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el curso con id: " + idCourse);
        }
        return products.stream()
    public List<ShowProductDTO> obtenerProductosPorCarrera(Integer idCarrera) {
        List<Product> productos = productRepository.findByCareer(idCarrera);
        if(productos.isEmpty()) {
            throw new ResourceNotFoundException(("No se encontraron productos para la carrera con id: " + idCarrera));
        }
        return productos.stream()
                .map(this::convertToShowProductDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCarreraPorCursoOrdenarPorVistas(Integer idCareer, Integer idCourse){
        List<Product> products = productRepository.findByCourseCareerOrderByViews(idCareer, idCourse);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la carera con id: " + idCareer + " y curso con id: " + idCourse);
        }
        return products.stream()
                .map(this::convertToShowProductDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCategoriaOrdernarPorVistas(Integer idCategory){
        List<Product> products = productRepository.findByCategoryOrderByViews(idCategory);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la categoria con id: " + idCategory);
        }
        return products.stream()
                .map(this::convertToShowProductDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCarreraPorCursoPorCategoriaOrdenarPorVistas(Integer idCareer, Integer idCourse, Integer idCategory){
        List<Product> products = productRepository.findByCareerCourseCategoryOrderByViews(idCareer, idCourse, idCategory);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la carrera con id: " + idCareer + " , curso con id: " + idCourse + " y categoria con id: " + idCategory);
        }
        return products.stream()

    @Transactional
    @Override
    public List<ShowProductDTO> obtenerProductosPorCurso(Integer idCurso) {
        List<Product> productos = productRepository.findByCourse(idCurso);
        if(productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el curso con id: " + idCurso);
        }
        return productos.stream()
                .map(this::convertToShowProductDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorIdVendedorOrdenarPorVistas(Integer idSeller){
        List<Product> products = productRepository.findBySellerIdOrderByViews(idSeller);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos del vendedor con id: " + idSeller);
        }
       return products.stream()

    @Transactional
    @Override
    public List<ShowProductDTO> obtenerProductosPorCarreraCursoYCategoria(Integer idCarrera, Integer idCurso, Integer idCategoria) {
        List<Product> productos = productRepository.findByCareerAndCourseAndCategorias(idCarrera, idCurso, idCategoria);
        if(productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la carrera con id: " + idCarrera + ",curso con id: " + idCurso + "y categoria con id: " + idCategoria);
        }
        return productos.stream()
                .map(this::convertToShowProductDTO)
                .collect(Collectors.toList());
    }
}
