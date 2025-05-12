package com.lintik.inventarioservice.controller;

import com.lintik.inventarioservice.model.Inventario;
import com.lintik.inventarioservice.service.InventarioService;
import com.lintik.inventarioservice.dto.CompraRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Inventario inventario) {
        Inventario creado = inventarioService.guardar(inventario);
        return ResponseEntity.ok(Map.of("data", creado));
    }

    @GetMapping("/{productoId}")
    public ResponseEntity<Map<String, Object>> obtener(@PathVariable Long productoId) {
        return inventarioService.obtenerPorProductoId(productoId)
                .map(inventario -> ResponseEntity.ok(Map.<String, Object>of("data", inventario)))
                .orElseGet(() -> ResponseEntity.status(404).body(Map.<String, Object>of(
                        "errors", List.of("Inventario no encontrado")
                )));
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<String> detalleInventario(@PathVariable Long id) {
        String respuesta = inventarioService.obtenerNombreProducto(id);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{productoId}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @PathVariable Long productoId,
            @RequestBody Map<String, Integer> cantidadRequest) {

        try {
            Integer cantidad = cantidadRequest.get("cantidad");

            if (cantidad == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "errors", List.of("El parámetro 'cantidad' es obligatorio")
                ));
            }

            boolean actualizado = inventarioService.actualizarStock(productoId, cantidad);
            if (actualizado) {
                return ResponseEntity.ok(Map.of("message", "Stock actualizado"));
            } else {
                return ResponseEntity.status(404).body(Map.of(
                        "errors", List.of("Producto no encontrado en inventario")
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "errors", List.of("Error inesperado: " + e.getMessage())
            ));
        }
    }

    @DeleteMapping("/{productoId}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long productoId) {
        boolean eliminado = inventarioService.eliminarPorProductoId(productoId);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "errors", List.of("Inventario no encontrado")
            ));
        }
    }

    // Endpoint para realizar compra
    @PutMapping("/comprar")
    public ResponseEntity<Inventario> realizarCompra(@RequestBody CompraRequest request) {
        try {
            Inventario actualizado = inventarioService.actualizarInventarioTrasCompra(
                    request.getProductoId(),
                    request.getCantidad()
            );
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarInventario() {
        List<Inventario> lista = inventarioService.listarTodo(); 
        return ResponseEntity.ok(Map.of("data", lista));
    }
}
