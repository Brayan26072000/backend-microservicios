package com.lintik.productosservice.service;

import com.lintik.productosservice.model.Producto;
import com.lintik.productosservice.repository.ProductoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    // ✅ Constructor necesario para pruebas unitarias
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> obtenerProducto(Long id) {
        return productoRepository.findById(id);
    }

    public Producto actualizarProducto(Long id, Producto datos) {
        return productoRepository.findById(id).map(p -> {
            p.setNombre(datos.getNombre());
            p.setPrecio(datos.getPrecio());
            return productoRepository.save(p);
        }).orElse(null);
    }

    public boolean eliminarProducto(Long id) {
        return productoRepository.findById(id).map(p -> {
            productoRepository.delete(p);
            return true;
        }).orElse(false);
    }

    public List<Producto> listarProductos(int page, int size) {
        return productoRepository.findAll(PageRequest.of(page, size)).getContent();
    }
}
