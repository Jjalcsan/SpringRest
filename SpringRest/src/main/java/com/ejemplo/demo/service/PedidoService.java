package com.ejemplo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplo.demo.model.LineaPedido;
import com.ejemplo.demo.model.Pedido;
import com.ejemplo.demo.repository.LineaPedidoRepository;
import com.ejemplo.demo.repository.PedidoRepository;
import com.ejemplo.demo.repository.ProductoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repoPed;
	
	@Autowired
	private ProductoRepository repoProd;
	
	@Autowired
	private LineaPedidoRepository repoLinPed;
	/**
	 * Busca todos los pedidos de la BBDD
	 * @return devuelve una lista de pedidos
	 */
	public List<Pedido> findAll() {
		
		return repoPed.findAll();
		
	}
	
	/**
	 * busca un pedido por su ID
	 * @param id
	 * @return devuelve el pedido si lo encuentra o null en caso contrario
	 */
	public Pedido findById(int id) {
		
		return repoPed.findById(id).orElse(null);
		
	}
	
	
	/**
	 * Añade un pedido a la BBDD
	 * @param pedido
	 * @return devuelve el pedido añadido
	 */
	public Pedido add(Pedido pedido) {
		
		return repoPed.save(pedido);
		
	}
	
	/**
	 * Borra un pedido de la BBDD por su ID
	 * @param id
	 * @return devuelve el pedido borrado o en caso contrario null
	 */
	public Pedido delete(int id) {
		
		if (repoPed.existsById(id)) {
			
			Pedido p = repoPed.findById(id).get();
			repoPed.deleteById(id);
			return p;
			
		} else {
			
			return null;
			
		}
	}
	
	/**
	 * Edita el metodo de envio de un Pedido
	 * @param id
	 * @param metodoEnvio
	 * @return Devuelve el pedido editado o null en caso contrario
	 */
	public Pedido edit(int id, String metodoEnvio) {
		
		if(repoPed.existsById(id)) {
			
			Pedido p = repoPed.findById(id).get();
			p.setMetodoEnvio(metodoEnvio);
			repoPed.save(p);
			return p;
			
		} else {
			
			return null;
			
		}
		
	}
	
	/**
	 * Calcula el precio total del pedido recorriendo las lineas y su cantidad
	 * @param pedido
	 * @return devuelve el precio total
	 */
	public double precioTotal(int id) {
		
		double total = 0;
		Pedido pedido = findById(id);
		List<LineaPedido> lineas = pedido.getLineasPedido();
		
		for(LineaPedido linea : lineas) {
			
			total += linea.getCantidad()*linea.getProducto().getPrecio();
			
		}
		
		return total;
		
	}
	
	/**
	 * Añade una nueva linea al pedido que se le pasa por ID, en caso de existir la linea cambia su cantidad a la nueva introducida
	 * @param idPed
	 * @param cantidad
	 * @param idProd
	 */
	public LineaPedido addLinPed(int pos, Pedido pedido, double cantidad) {
		
		LineaPedido newLinea = new LineaPedido(repoProd.findAll().get(pos - 1), pedido);

		List<LineaPedido> lineasPedido = pedido.getLineasPedido();

		if (lineasPedido.contains(newLinea)) {

			int indice = lineasPedido.indexOf(newLinea);
			LineaPedido lineaPedido = lineasPedido.get(indice);
			double cantidadOld = lineaPedido.getCantidad();
			double cantidadNueva = cantidadOld + cantidad;
			lineaPedido.setCantidad(cantidadNueva);
			repoLinPed.save(lineaPedido);
		} else {
			
			newLinea.setCantidad(cantidad);
			lineasPedido.add(newLinea);
			repoLinPed.save(newLinea);

		}
		
		return newLinea;
	}
		
	
}
