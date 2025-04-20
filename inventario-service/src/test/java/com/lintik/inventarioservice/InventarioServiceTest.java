package com.lintik.inventarioservice;

import com.lintik.inventarioservice.model.Inventario;
import com.lintik.inventarioservice.repository.InventarioRepository;
import com.lintik.inventarioservice.service.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;  // Mock de InventarioRepository

    @InjectMocks
    private InventarioService inventarioService;  // Inyección automática del mock en InventarioService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks antes de cada prueba
    }

    @Test
    void testActualizarInventarioTrasCompra() {
        // Preparación de datos
        Inventario inventario = new Inventario(1L, 10);  // Inventario inicial
        when(inventarioRepository.findByProductoId(1L)).thenReturn(Optional.of(inventario));  // Mock del findByProductoId
        when(inventarioRepository.save(any())).thenReturn(new Inventario(1L, 8));  // Mock del save

        // Llamada al servicio
        Inventario actualizado = inventarioService.actualizarInventarioTrasCompra(1L, 2);

        // Verificación de resultados
        assertEquals(8, actualizado.getCantidad());  // Verifica que la cantidad se haya reducido correctamente
    }

    @Test
    void testActualizarInventario_insuficiente() {
        // Preparación de datos
        Inventario inventario = new Inventario(1L, 1);  // Inventario inicial con 1 producto
        when(inventarioRepository.findByProductoId(1L)).thenReturn(Optional.of(inventario));  // Mock del findByProductoId

        // Verificación de excepción cuando la cantidad solicitada es mayor que la disponible
        assertThrows(RuntimeException.class, () -> {
            inventarioService.actualizarInventarioTrasCompra(1L, 5);  // Intentar actualizar con una cantidad mayor
        });
    }
}