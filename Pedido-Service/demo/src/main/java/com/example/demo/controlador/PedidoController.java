package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entidades.Pedido;
import com.example.demo.modelos.Producto;
import com.example.demo.servicio.PedidoServicio;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

@Autowired
private PedidoServicio pedidoServicio;

@PostMapping
public ResponseEntity<Pedido> guardarPedido(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoServicio.save(pedido));
    }

@GetMapping
public ResponseEntity<List<Pedido>> ListarPedido(){
    List<Pedido> pedidos = pedidoServicio.getAll();
    if(pedidos.isEmpty()){
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(pedidos);
}

@GetMapping("/{id}")
public ResponseEntity<Pedido> obtenerPedido(@PathVariable("id")int id){
    Pedido pedido = pedidoServicio.getPedidoById(id);
    if(pedido == null){
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(pedido);
}

    @PostMapping
    ("/productos/{PedidoId}")
public ResponseEntity<Producto> agregarProducto(
    @PathVariable int PedidoId,
    @RequestBody Producto producto) {
    
    // Lógica para guardar el producto
    Producto nuevoProducto = pedidoServicio.agregarProducto(PedidoId, producto);
    return ResponseEntity.ok(nuevoProducto);
}
@DeleteMapping("/{id}")
public ResponseEntity<Void> eliminarPedido(@PathVariable("id") int id) {
    Pedido pedido = pedidoServicio.getPedidoById(id);
    if(pedido == null) {
        return ResponseEntity.notFound().build();
    }
    pedidoServicio.deletePedidoById(id);
    return ResponseEntity.noContent().build();
}

}
