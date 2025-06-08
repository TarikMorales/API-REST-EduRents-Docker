package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.dto.product.*;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.model.entity.product.*;
import com.ingsoft.tf.api_edurents.model.entity.university.CoursesCareers;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.repository.product.*;
import com.ingsoft.tf.api_edurents.repository.university.CoursesCareersRepository;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
import com.ingsoft.tf.api_edurents.service.AdminProductService;
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

    private StockDTO convertToProductStockDTO(Product producto) {
        StockDTO dto = new StockDTO();
        dto.setId(producto.getId());
        dto.setCantidad_disponible(producto.getCantidad_disponible());
        return dto;
    }
    public ShowProductDTO convertToShowProductDTO(Product producto) {

        ShowProductDTO productoDTOMostrar = new ShowProductDTO();

        productoDTOMostrar.setId(producto.getId());
        productoDTOMostrar.setNombre(producto.getNombre());
        productoDTOMostrar.setDescripcion(producto.getDescripcion());
        productoDTOMostrar.setPrecio(producto.getPrecio());
        productoDTOMostrar.setEstado(producto.getEstado());
        productoDTOMostrar.setFecha_creacion(producto.getFecha_creacion());
        productoDTOMostrar.setFecha_modificacion(producto.getFecha_modificacion());
        productoDTOMostrar.setFecha_expiracion(producto.getFecha_expiracion());
        productoDTOMostrar.setCantidad_disponible(producto.getCantidad_disponible());
        productoDTOMostrar.setAcepta_intercambio(producto.getAcepta_intercambio());

        // Asignar vendedor
        if (producto.getVendedor() != null) {
            SellerDTO vendedorDTO = new SellerDTO();
            vendedorDTO.setId(producto.getVendedor().getId());
            vendedorDTO.setResena(producto.getVendedor().getResena());
            vendedorDTO.setConfiabilidad(producto.getVendedor().getConfiabilidad());
            vendedorDTO.setSin_demoras(producto.getVendedor().getSin_demoras());
            vendedorDTO.setBuena_atencion(producto.getVendedor().getBuena_atencion());
            productoDTOMostrar.setVendedor(vendedorDTO);
        }

        // Asignar imagenes
        if (producto.getImagenes() != null) {
            List<ImageDTO> imagenesDTO = producto.getImagenes().stream()
                    .map(imagen -> {
                        ImageDTO imagenDTO = new ImageDTO();
                        imagenDTO.setId(imagen.getId());
                        imagenDTO.setUrl(imagen.getUrl());
                        return imagenDTO;
                    }).collect(Collectors.toList());
            productoDTOMostrar.setImagenes(imagenesDTO);
        }

        // Asignar categorias
        if (producto.getCategorias() != null) {
            List<CategoryDTO> categoriasDTO = producto.getCategorias().stream()
                    .map(cat -> {
                        CategoryDTO categoriaDTO = new CategoryDTO();
                        categoriaDTO.setId(cat.getCategoria().getId());
                        categoriaDTO.setNombre(cat.getCategoria().getNombre());
                        return categoriaDTO;
                    }).collect(Collectors.toList());
            productoDTOMostrar.setCategorias(categoriasDTO);
        }

        // Asignar cursos y carreras
        if (producto.getCursos_carreras() != null) {
            List<CourseCareerDTO> cursosCarrerasDTO = producto.getCursos_carreras().stream()
                    .map(cursoCarrera -> {
                        CourseCareerDTO cursoCarreraDTO = new CourseCareerDTO();
                        cursoCarreraDTO.setId_carrera(cursoCarrera.getCurso_carrera().getCarrera().getId());
                        cursoCarreraDTO.setCarrera(cursoCarrera.getCurso_carrera().getCarrera().getNombre());
                        cursoCarreraDTO.setId_curso(cursoCarrera.getCurso_carrera().getCurso().getId());
                        cursoCarreraDTO.setCurso(cursoCarrera.getCurso_carrera().getCurso().getNombre());
                        return cursoCarreraDTO;
                    }).collect(Collectors.toList());
            productoDTOMostrar.setCursos_carreras(cursosCarrerasDTO);
        }

        return productoDTOMostrar;
    }

    private Product convertToProduct(Product producto, ProductDTO productoDTO, String tipo) {
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setEstado(productoDTO.getEstado());
        producto.setFecha_creacion(LocalDate.now());
        if (tipo.equals("editar")) {
            producto.setFecha_modificacion(LocalDate.now());
        }
        producto.setCantidad_disponible(productoDTO.getCantidad_disponible());
        producto.setAcepta_intercambio(productoDTO.getAcepta_intercambio());

        Seller vendedor = sellerRepository.findById(productoDTO.getId_vendedor())
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado"));
        producto.setVendedor(vendedor);

        // Guardamos el producto
        productRepository.save(producto);

        // Asignamos las imagenes al producto
        producto.getImagenes().clear();
        if (productoDTO.getUrls_imagenes() != null) {
            List<Image> imagenes = productoDTO.getUrls_imagenes().stream()
                    .map(url -> {
                        Image imagen = new Image();
                        imagen.setUrl(url);
                        imagen.setProducto(producto);
                        imageRepository.save(imagen);
                        return imagen;
                    }).toList();
            producto.getImagenes().addAll(imagenes);
        }

        // Categorias del producto
        producto.getCategorias().clear();
        if (productoDTO.getCategorias() != null) {
            List<CategoriesProducts> categorias = productoDTO.getCategorias().stream()
                    .map(catId -> {
                        Category categoria = categoryRepository.findById(catId)
                                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + catId));
                        CategoriesProducts rel = new CategoriesProducts();
                        rel.setProducto(producto);
                        rel.setCategoria(categoria);
                        categoriesProductsRepository.save(rel);
                        return rel;
                    }).toList();
            producto.getCategorias().addAll(categorias);
        }

        // Cursos y carreras del producto
        producto.getCursos_carreras().clear();
        if (productoDTO.getCursos_carreras() != null) {
            List<CoursesCareersProduct> cursosCarreras = productoDTO.getCursos_carreras().stream()
                    .map(id -> {
                        CoursesCareers cursoCarrera = coursesCareersRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Curso/Carrera no encontrado con id: " + id));
                        CoursesCareersProduct rel = new CoursesCareersProduct();
                        rel.setProducto(producto);
                        rel.setCurso_carrera(cursoCarrera);
                        coursesCareersProductRepository.save(rel);
                        return rel;
                    }).toList();
            producto.getCursos_carreras().addAll(cursosCarreras);
        }

        return producto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerTodosLosProductos() {
        List<Product> productos = productRepository.findAll();
        return productos.stream()
                .map(this::convertToShowProductDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ShowProductDTO crearProducto(ProductDTO productoDTO) {

        Product producto = new Product();
        // Convertimos el DTO a entidad
        producto = convertToProduct(producto, productoDTO, "crear");

        // Convertimos a DTO para devolver
        ShowProductDTO productoDTOMostrar = convertToShowProductDTO(producto);
        return productoDTOMostrar;
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
        ShowProductDTO productoDTOMostrar = convertToShowProductDTO(producto);
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
        producto = convertToProduct(producto, productoDTO, "editar");
        // Convertimos a DTO para devolver
        ShowProductDTO productoDTOMostrar = convertToShowProductDTO(producto);
        return productoDTOMostrar;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorVendedor(Integer idVendedor) {
        List<Product> productos = productRepository.findByVendedor(idVendedor);
        if (productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el vendedor con id: " + idVendedor);
        }
        return productos.stream()
                .map(this::convertToShowProductDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCursoYCarrera(Integer idCarrera, Integer idCurso) {
        List<Product> productos = productRepository.findByCareerAndCourse(idCarrera, idCurso);
        if (productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la carrera con id: " + idCarrera + " y curso con id: " + idCurso);
        }
        return productos.stream()
                .map(this::convertToShowProductDTO)
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
                .map(this::convertToShowProductDTO)
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
    public List<ShowProductDTO> obtenerProductosPorCarrera(Integer idCarrera) {
        List<Product> productos = productRepository.findByCareer(idCarrera);
        if(productos.isEmpty()) {
            throw new ResourceNotFoundException(("No se encontraron productos para la carrera con id: " + idCarrera));
        }
        return productos.stream()
                .map(this::convertToShowProductDTO)
                .collect(Collectors.toList());
    }

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
