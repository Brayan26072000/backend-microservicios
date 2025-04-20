package com.lintik.productosservice.controller;

import com.lintik.productosservice.model.Producto;
import com.lintik.productosservice.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Producto producto) {
        Producto creado = productoService.crearProducto(producto);
        return ResponseEntity.ok(Map.of("data", (Object) creado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtener(@PathVariable Long id) {
        return productoService.obtenerProducto(id)
                .map(p -> ResponseEntity.ok(Map.of("data", (Object) p)))
                .orElse(ResponseEntity.status(404).body(Map.of("errors", List.of("Producto no encontrado"))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        Producto actualizado = productoService.actualizarProducto(id, producto);
        if (actualizado != null) {
            return ResponseEntity.ok(Map.of("data", (Object) actualizado));
        }
        return ResponseEntity.status(404).body(Map.of("errors", List.of("Producto no encontrado")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        boolean eliminado = productoService.eliminarProducto(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(404).body(Map.of("errors", List.of("Producto no encontrado")));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        List<Producto> productos = productoService.listarProductos(page, size);
        return ResponseEntity.ok(Map.of("data", (Object) productos));
    }
}
