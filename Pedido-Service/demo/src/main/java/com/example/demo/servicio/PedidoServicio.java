package com.example.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entidades.Pedido;
import com.example.demo.modelos.Producto;
import com.example.demo.repositorio.PedidoRepository;

@Service
public class PedidoServicio {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PedidoRepository pedidoRepository;
    public List<Pedido>getAll(){
        return pedidoRepository.findAll();
    }
    public Pedido getPedidoById(int id){
        return pedidoRepository.findById(id).orElse(null);
    }
    public Pedido save(Pedido pedido){
        Pedido nuevoPedido = pedidoRepository.save(pedido);
        return nuevoPedido;
    }
    public List<Producto> getProductos(int pedidoId) {
        List<Producto> productos = restTemplate.getForObject(
            "http://localhost:8080/pedido/" + pedidoId,
            List.class);
        return productos;
    }

    public Producto agregarProducto(int pedidoId, Producto producto) {
    String url = "http://localhost:8080/pedido/" + pedidoId;
    return restTemplate.postForObject(url, producto, Producto.class);
}
public void deletePedidoById(int id){
    pedidoRepository.deleteById(id);
}
}
