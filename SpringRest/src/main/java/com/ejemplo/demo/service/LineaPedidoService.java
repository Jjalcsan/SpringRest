package com.ejemplo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplo.demo.model.LineaPedido;
import com.ejemplo.demo.model.Pedido;
import com.ejemplo.demo.repository.LineaPedidoRepository;
import com.ejemplo.demo.repository.PedidoRepository;

@Service
public class LineaPedidoService {

	@Autowired
	private LineaPedidoRepository repoLinPed;
	
	@Autowired
	private PedidoService servicePed;
	
	@Autowired
	private PedidoRepository repoPed;
	
	/**
	 * Busca todas las lineas de pedido de la BBDD
	 * @return devuelve la lista de lineas
	 */
	public List<LineaPedido> findAll(){
		
		return repoLinPed.findAll();
		
	}
	
	/**
	 * Busca una linea por ID
	 * @param id
	 * @return devuelve la linea si existe o null en caso contrario
	 */
	public LineaPedido findById(int id) {
		
		return repoLinPed.findById(id).orElse(null);
		
	}
	
	/**
	 * Añade una nueva linea a la BBDD
	 * @param linea
	 * @return devuelve la linea guardada
	 */
	public LineaPedido add(LineaPedido linea) {
		
		return repoLinPed.save(linea);
		
	}
	
	/**
	 * Edita la cantidad de una de las lineas
	 * @param id
	 * @param cantidad
	 * @return devuelve la linea de pedido editada o null en caso de que no exista
	 */
	public LineaPedido edit(int id, double cantidad) {
		
		int idPedido = findById(id).getPedido().getId();
		Pedido pedido = servicePed.findById(idPedido);
		int posicionLinea = pedido.getLineasPedido().indexOf(findById(id));	
		LineaPedido linea = pedido.getLineasPedido().get(posicionLinea);
		
		linea.setCantidad(cantidad);
		repoLinPed.save(linea);
		pedido.setTotal(servicePed.precioTotal(pedido.getId()));
		repoPed.save(pedido);
		
		return linea;
		
	}
	
	/**
	 * Borra una linea de pedido de la BBDD
	 * @param id
	 * @return devuelve la linea borrada
	 */
	public void delete(int id) {
		
		int idPedido = findById(id).getPedido().getId();
		Pedido pedido = servicePed.findById(idPedido);
		int posicionLinea = pedido.getLineasPedido().indexOf(findById(id));
		LineaPedido linea =pedido.getLineasPedido().get(posicionLinea);
		
		pedido.getLineasPedido().remove(linea);
		repoLinPed.delete(linea);
		pedido.setTotal(servicePed.precioTotal(pedido.getId()));
		repoPed.save(pedido);
		
		Pedido aux = repoPed.getById(pedido.getId());
		aux.setLineasPedido(pedido.getLineasPedido());
		
	}
}
