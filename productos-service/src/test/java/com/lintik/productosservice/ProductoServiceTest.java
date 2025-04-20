package com.lintik.productosservice;

import com.lintik.productosservice.model.Producto;
import com.lintik.productosservice.repository.ProductoRepository;
import com.lintik.productosservice.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    private final ProductoRepository productoRepository = mock(ProductoRepository.class);
    private final ProductoService productoService = new ProductoService(productoRepository);

    @Test
    void testCrearProducto() {
        Producto nuevo = new Producto(null, "Zapato", 200.0);
        Producto guardado = new Producto(1L, "Zapato", 200.0);

        when(productoRepository.save(nuevo)).thenReturn(guardado);

        Producto resultado = productoService.crearProducto(nuevo);
        assertNotNull(resultado);
        assertEquals("Zapato", resultado.getNombre());
    }

    @Test
    void testObtenerProductoPorIdExistente() {
        Producto producto = new Producto(1L, "Camisa", 100.0);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = productoService.obtenerProducto(1L); // <-- corregido aquí
        assertTrue(resultado.isPresent());
        assertEquals("Camisa", resultado.get().getNombre());
    }
}
