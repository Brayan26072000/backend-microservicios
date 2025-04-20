package com.lintik.inventarioservice.service;

import com.lintik.inventarioservice.model.Inventario;
import com.lintik.inventarioservice.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private WebClient productosWebClient;

    // Consulta externa al microservicio de productos
    public String obtenerNombreProducto(Long productoId) {
        try {
            return productosWebClient.get()
                    .uri("/{id}", productoId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(3))
                    .block(); // bloqueante
        } catch (Exception e) {
            return "{\"error\": \"Producto no disponible\"}";
        }
    }

    public Inventario guardar(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public Optional<Inventario> obtenerPorProductoId(Long productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    public boolean actualizarStock(Long productoId, int nuevaCantidad) {
        Optional<Inventario> optionalInventario = inventarioRepository.findByProductoId(productoId);
        if (optionalInventario.isPresent()) {
            Inventario inventario = optionalInventario.get();
            inventario.setCantidad(nuevaCantidad);
            inventarioRepository.save(inventario);
            return true;
        }
        return false;
    }

    public boolean eliminarPorProductoId(Long productoId) {
        Optional<Inventario> optionalInventario = inventarioRepository.findByProductoId(productoId);
        if (optionalInventario.isPresent()) {
            inventarioRepository.delete(optionalInventario.get());
            return true;
        }
        return false;
    }

    // actualiza el inventario tras una compra
    public Inventario actualizarInventarioTrasCompra(Long productoId, Integer cantidad) {
        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        if (inventario.getCantidad() < cantidad) {
            throw new RuntimeException("Inventario insuficiente");
        }

        inventario.setCantidad(inventario.getCantidad() - cantidad);

        // Emitir evento simple en consola
        System.out.println("🛒 Inventario actualizado para producto " + productoId + ": " + inventario.getCantidad());

        return inventarioRepository.save(inventario);
    }
    public List<Inventario> listarTodo() {
        return inventarioRepository.findAll();
    }
}
