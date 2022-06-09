package com.ejemplo.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.demo.exception.LineaPedidoNotFoundException;
import com.ejemplo.demo.exception.PedidoNotFoundException;
import com.ejemplo.demo.model.LineaPedido;
import com.ejemplo.demo.model.Pedido;
import com.ejemplo.demo.model.Producto;
import com.ejemplo.demo.model.Usuario;
import com.ejemplo.demo.repository.LineaPedidoRepository;
import com.ejemplo.demo.repository.PedidoRepository;
import com.ejemplo.demo.repository.UsuarioRepository;
import com.ejemplo.demo.service.LineaPedidoService;
import com.ejemplo.demo.service.PedidoService;
import com.ejemplo.demo.service.ProductoService;

@RestController
public class CarritoController {

	/**
	 * Cambiar rutas
	 * No puede haber 2 pathvariables juntas
	 * El controlador no accede al repository
	 * Cambiar excepciones: Solo mostrar el mensaje, en caso
	 * de que no se encuentre el recurso dar 404 y no 500
	 * put debe de recibir objetos
	 * al hacer delete devolver 204 no content
	 * Sustituir requestParams por requestBody
	 * limitar la informacion que devuelven los metodos (No mostrar pedidos en usuarios p.ej)
	 */
	
	@Autowired
	private PedidoService servicePed;
	
	@Autowired
	private ProductoService serviceProd;
	
	@Autowired
	private LineaPedidoService serviceLinPed;
	
	@Autowired
	private PedidoRepository repoPed;
	
	@Autowired
	private UsuarioRepository repoUsu;
	
	@Autowired
	private LineaPedidoRepository repoLinPed;

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Metodos de los productos
	
	/**
	 * Metodo de consulta para encontrar todos los productos de la BBDD
	 * @return nos devuelve la lista de productos
	 */
	@GetMapping("/productos")
	public ResponseEntity<List<Producto>> findAll() {
		
		List<Producto> catalogo = serviceProd.findAll();
		ResponseEntity<List<Producto>> findall = ResponseEntity.ok(catalogo);
		
		if(catalogo == null) {
			
			findall = ResponseEntity.notFound().build();
			
		}
		
		return findall;
		
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Metodos de los pedidos	

	/**
	 * Metodo de consulta para encontrar todos los pedidos de la BBDD
	 * @return nos devuelve la lista de pedidos
	 */
	@GetMapping("/pedidos")
	public ResponseEntity<List<Pedido>> findAllPed() {
		
		List<Pedido> pedidos = servicePed.findAll();
		ResponseEntity<List<Pedido>> findall = ResponseEntity.ok(pedidos);
		
		if(pedidos == null) {
			
			findall = ResponseEntity.notFound().build();
			
		}
		
		return findall;
		
	}
	
	/**
	 * Metodo de consulta para encontrar un pedido por su ID
	 * @param id
	 * @return Devuelve el pedido o una excepcion en caso contrario
	 */
	@GetMapping("/pedidos/{id}")
	public ResponseEntity<Pedido> findByIdProd(@PathVariable int id)throws Exception{
		
		Pedido pedido = servicePed.findById(id);
		ResponseEntity<Pedido> findbyid;
		
		if(pedido != null) {
			
			findbyid = ResponseEntity.ok(pedido);
			
		} else {
			
			throw new PedidoNotFoundException(id);
			//En el caso de que no exista ese pedido lanzará la excepcion propia
			
		}
		
		return findbyid;
		
		
	}
	
	/**
	 * Crea un pedido vacio para el usuario indicado en la ruta
	 * @param idUsu
	 * @return Devolvera el pedido creado o lanzara una excepcion si no existe el usuario
	 */
	@PostMapping("/pedidos/{idUsu}")
	public ResponseEntity<Pedido> newPedido(@PathVariable String idUsu)throws Exception{
			
		
			Pedido pedido = new Pedido();
			servicePed.add(pedido);
			Usuario usuBBDD = repoUsu.findById(idUsu).orElse(null);
			usuBBDD.getPedidos().add(0, pedido);
			repoUsu.save(usuBBDD);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
	
	}

	/**
	 * Metodo para eliminar un pedido por su ID
	 * @param idUsu
	 * @param idPed
	 */
	@DeleteMapping("/pedidos/{idUsu}/{idPed}")
	public Pedido eliminarPedido(@PathVariable int idPed, @PathVariable String idUsu)throws Exception {
		
		Pedido pedido = servicePed.findById(idPed);
		Usuario usuBBDD = repoUsu.findById(idUsu).orElse(null);
		
		if (pedido == null) {
			
			throw new PedidoNotFoundException(idPed);
			//En el caso de que no exista ese pedido lanzará la excepcion propia
			
		} else {
			
			usuBBDD.getPedidos().remove(pedido);
			servicePed.delete(idPed);
			
		}
		
		return pedido;
		
	}
	
	/**
	 * Cambia el tipo de envio y el precio total del pedido pasado por la ID, en caso de no existir el pedido lanzara una excepcion
	 * @param metodo
	 * @param idPed
	 * @throws Exception
	 */
	@PutMapping("/pedidos/{idPed}")
	public Pedido editarPedido(@RequestBody String metodo, @PathVariable int idPed)throws Exception {
		
		Pedido pedido = servicePed.findById(idPed);
		
		if(pedido != null ) {
			
			pedido.setMetodoEnvio(metodo);
			pedido.setTotal(servicePed.precioTotal(idPed));
			repoPed.save(pedido);
			
		} else {
			
			throw new PedidoNotFoundException(idPed);
			//En el caso de que no exista ese pedido lanzará la excepcion propia
			
		}
		
		return pedido;
		
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Metodos de los pedidos
	
	/**
	 * Metodo de consulta para buscar todas las lineas del pedido indicado en la ruta
	 * @param idPed
	 * @return Devolvera las lineas del pedido o lanzara una excepcion si no existe el pedido
	 */
	@GetMapping("/lineas/{idPed}")
	public ResponseEntity<List<LineaPedido>> findAllLineas(@PathVariable int idPed)throws Exception{
		
		List<LineaPedido> findall = servicePed.findById(idPed).getLineasPedido();
		ResponseEntity<List<LineaPedido>> linPedFind;
		
		if(findall != null) {
			
			linPedFind = ResponseEntity.ok(findall);
			
		} else {
			
			throw new PedidoNotFoundException(idPed);
			//En el caso de que no exista ese pedido lanzará la excepcion propia
			
		}
		
		return linPedFind;
		
	}
	
	/**
	 * Metodo para editar una linea de un pedido de la BBDD mediante el id de la linea y el del pedido
	 * @param idPed
	 * @param idLinPed
	 * @param cantidad
	 */
	@PutMapping("/lineas/{idPed}/{idLinPed}")
	public LineaPedido putNuevaCantidad(@PathVariable int idPed, @PathVariable int idLinPed, @RequestParam double cantidad) {
		
		Pedido pedido = servicePed.findById(idPed);
		LineaPedido linea = repoLinPed.getById(idLinPed);
		
		if (pedido == null) {
			
			throw new PedidoNotFoundException(idPed);
			//En el caso de que no exista ese pedido lanzará la excepcion propia
			
		} else if (linea == null){
			
			throw new LineaPedidoNotFoundException(idLinPed);
			
		} else {
			
			serviceLinPed.edit(idLinPed, cantidad);
			pedido.setTotal(servicePed.precioTotal(pedido.getId()));
			
		}
		
		return linea;
			
	}
	
	/**
	 * Método para borrar una linea de un pedido de la BBDD mediante el id de la linea y
	 * el id del pedido que sera especificado en la ruta
	 * @param idPed
	 * @param idLinPed
	 */
	@DeleteMapping("/lineas/{idPed}/{idLinPed}")
	public LineaPedido deleteLinPed(@PathVariable int idPed, @PathVariable int idLinPed) {

		Pedido pedido = servicePed.findById(idPed);
		LineaPedido linea = repoLinPed.getById(idLinPed);
		
		if (pedido == null) {
			
			throw new PedidoNotFoundException(idPed);
			//En el caso de que no exista ese pedido lanzará la excepcion propia
			
		} else if (linea == null){
			
			throw new LineaPedidoNotFoundException(idLinPed);
			
		} else {
			
			serviceLinPed.delete(idLinPed);
			repoLinPed.delete(linea);
			
		}
		
		return linea;
		
	}
	
	/**
	 * Añade una nueva linea al pedido con el producto y la cantidad especificada
	 * @param idPed
	 * @param idProd
	 * @param cant
	 * @throws Exception
	 */
	@PostMapping("/lineas/{idPed}")
	public Pedido addLinea(@PathVariable int idPed, @RequestParam int idProd, @RequestParam double cant)throws Exception {
		
		Pedido pedido = servicePed.findById(idPed);
		
		if (pedido == null) {
			
			throw new PedidoNotFoundException(idPed);
			//En el caso de que no exista ese pedido lanzará la excepcion propia
			
		} else {
			
			servicePed.addLinPed(idProd, pedido, cant);
			pedido.setTotal(servicePed.precioTotal(pedido.getId())); 
			
		}
		
		return pedido;
		
	}
	
}
