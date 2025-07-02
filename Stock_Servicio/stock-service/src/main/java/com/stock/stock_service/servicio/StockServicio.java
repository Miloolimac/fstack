package com.stock.stock_service.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stock.stock_service.entidades.Stock;
import com.stock.stock_service.modelos.Producto;
import com.stock.stock_service.modelos.StockCompleto;
import com.stock.stock_service.repositorio.StockRepository;

@Service
public class StockServicio {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private RestTemplate restTemplate;

    //obtener todos los registros de stock
    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    //obtener stock por id de producto
    public Stock getByProductoId(int id) {
        return stockRepository.findById(id).orElse(null);
    }

    //guardar o actualizar stock
    public Stock guardar(Stock stock) {
        return stockRepository.save(stock);
    }

    //obtener productos con stock crítico (valor fijo 5 por ahora)
    public List<Stock> getCritico() {
        return stockRepository.findByCantidadActualLessThanEqual(5);
    }

    //obtener datos del producto desde producto-service
    public Producto getProductoDesdeServicio(int productoId) {
        Producto producto = restTemplate.getForObject("http://localhost:8002/producto/" + productoId, Producto.class);
        return producto;
    }

    //devolver stock combinado con datos del producto (por id)
    public StockCompleto getStockConProducto(int productoId) {
        Stock stock = getByProductoId(productoId);
        if (stock == null) return null;

        Producto producto = getProductoDesdeServicio(productoId);
        return new StockCompleto(
            stock.getProductoId(),
            stock.getCantidadActual(),
            stock.getStockMinimo(),
            producto
        );
    }

    //devolver lista de productos con stock crítico incluyendo sus datos de todos
    public List<StockCompleto> getCriticoConProducto() {
        List<Stock> criticos = getCritico();
        return criticos.stream().map(stock -> {
            Producto producto = getProductoDesdeServicio(stock.getProductoId());
            return new StockCompleto(
                stock.getProductoId(),
                stock.getCantidadActual(),
                stock.getStockMinimo(),
                producto
            );
        }).collect(Collectors.toList());
    }

    //obtener todos los stocks con sus productos asociados
    public List<StockCompleto> getTodosConProducto() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream().map(stock -> {
            Producto producto = getProductoDesdeServicio(stock.getProductoId());
            return new StockCompleto(
                stock.getProductoId(),
                stock.getCantidadActual(),
                stock.getStockMinimo(),
                producto
            );
        }).collect(Collectors.toList());
    }
}