package com.stock.stock_service.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stock.stock_service.entidades.Stock;
import com.stock.stock_service.modelos.Producto;
import com.stock.stock_service.modelos.StockCompleto;
import com.stock.stock_service.servicio.StockServicio;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockServicio stockService;

    //LISTAR TODOS con productos incluidos
    @GetMapping
    public ResponseEntity<List<StockCompleto>> listarStockCompleto() {
        List<StockCompleto> lista = stockService.getTodosConProducto();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<StockCompleto>> obtenerStockConProducto(@PathVariable("id") int id) {
    StockCompleto respuesta = stockService.getStockConProducto(id);
    if (respuesta == null) {
        return ResponseEntity.notFound().build();
    }

    EntityModel<StockCompleto> recurso = EntityModel.of(respuesta);

    // Enlaces HATEOAS
    recurso.add(linkTo(methodOn(StockController.class).obtenerStockConProducto(id)).withSelfRel());
    recurso.add(linkTo(StockController.class).slash("").withRel("todos-los-stocks"));
    recurso.add(linkTo(StockController.class).slash("stock/critico").withRel("stock-critico"));

    return ResponseEntity.ok(recurso);
    }

    //guardar o actualizar stock
    @PostMapping
    public ResponseEntity<Stock> guardarStock(@RequestBody Stock stock) {
        Stock nuevo = stockService.guardar(stock);
        return ResponseEntity.ok(nuevo);
    }

    //obtener solo el producto por id sin stock
    @GetMapping("/{id}/producto")
    public ResponseEntity<Producto> obtenerProductoRelacionado(@PathVariable("id") int productoId) {
        Producto producto = stockService.getProductoDesdeServicio(productoId);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    //listar productos con stock crítico incluyendo datos del producto
    @GetMapping("/critico")
    public ResponseEntity<List<StockCompleto>> stockCriticoConProducto() {
        List<StockCompleto> criticos = stockService.getCriticoConProducto();
        if (criticos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(criticos);
    }
}