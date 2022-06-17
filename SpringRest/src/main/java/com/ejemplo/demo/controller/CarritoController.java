package com.ejemplo.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.demo.exception.ApiError;
import com.ejemplo.demo.exception.LineaPedidoNotFoundException;
import com.ejemplo.demo.exception.NoContentException;
import com.ejemplo.demo.exception.PedidoNotFoundException;
import com.ejemplo.demo.exception.ProductoNotFoundException;
import com.ejemplo.demo.exception.UsuarioNotFoundException;
import com.ejemplo.demo.model.LineaPedido;
import com.ejemplo.demo.model.Pedido;
import com.ejemplo.demo.model.Producto;
import com.ejemplo.demo.model.Usuario;
import com.ejemplo.demo.service.LineaPedidoService;
import com.ejemplo.demo.service.PedidoService;
import com.ejemplo.demo.service.ProductoService;
import com.ejemplo.demo.service.UsuarioService;

@RestController
public class CarritoController {
	
	@Autowired
	private PedidoService servicePed;
	
	@Autowired
	private ProductoService serviceProd;
	
	@Autowired
	private LineaPedidoService serviceLinPed;
	
	@Autowired
	private UsuarioService serviceUsu;

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
	public List<Pedido> findAllPed() throws Exception{
		
		List<Pedido> pedidos = servicePed.findAll();
		
		if(pedidos.size()==0) {
			
			throw new NoContentException();
			
		}
		
		return pedidos;
		
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
	@PostMapping("/usuarios/{idUsu}/pedidos")
	public ResponseEntity<Pedido> newPedido(@PathVariable String idUsu)throws Exception{
			
		
			Pedido pedido = new Pedido();
			servicePed.add(pedido);
			Usuario usuBBDD = serviceUsu.findById(idUsu);
			usuBBDD.getPedidos().add(0, pedido);
			serviceUsu.save(usuBBDD);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
	
	}

	/**
	 * Metodo para eliminar un pedido por su ID
	 * @param idUsu
	 * @param idPed
	 */
	@DeleteMapping("/usuarios/{idUsu}/pedidos/{idPed}")
	public void eliminarPedido(@PathVariable int idPed, @PathVariable String idUsu)throws Exception {
		
		Pedido pedido = servicePed.findById(idPed);
		Usuario usuBBDD = serviceUsu.findById(idUsu);
		
		if (pedido == null) {
			
			throw new PedidoNotFoundException(idPed);
			//En el caso de que no exista ese pedido lanzará la excepcion propia
			
		} else {
			
			usuBBDD.getPedidos().remove(pedido);
			servicePed.delete(idPed);
			
		}
		
		throw new NoContentException();
		
	}
	
	/**
	 * Cambia el tipo de envio y el precio total del pedido pasado por la ID, en caso de no existir el pedido lanzara una excepcion
	 * @param metodo
	 * @param idPed
	 * @throws Exception
	 */
	@PutMapping("/pedidos/{idPed}")
	public Pedido editarPedido(@RequestBody Pedido pedido, @PathVariable int idPed)throws Exception {
		
		if(pedido != null ) {
			
			servicePed.add(pedido);
			
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
	@GetMapping("/pedidos/{idPed}/lineas")
	public List<LineaPedido> findAllLineas(@PathVariable int idPed)throws Exception{
		
		List<LineaPedido> findall = servicePed.findById(idPed).getLineasPedido();
		
		if(findall.size() == 0) {
			
			throw new NoContentException();
			//En el caso de que no exista ese pedido lanzará la excepcion propia
		
		}
		
		return findall;
		
	}
	
	/**
	 * Metodo para editar una linea de un pedido de la BBDD mediante el id de la linea y el del pedido
	 * @param idPed
	 * @param idLinPed
	 * @param cantidad
	 */
	@PutMapping("/pedidos/{idPed}/lineas/{idLinPed}")
	public LineaPedido putNuevaCantidad(@PathVariable int idPed, @PathVariable int idLinPed, @RequestBody LineaPedido linPed) {
		
		Pedido pedido = servicePed.findById(idPed);
		
		if (pedido == null) {
			
			throw new PedidoNotFoundException(idPed);
			//En el caso de que no exista ese pedido lanzará la excepcion propia
			
		} else if (linPed == null){
			
			throw new LineaPedidoNotFoundException(idLinPed);
			
		} else {
			
			serviceLinPed.add(linPed);
			pedido.setTotal(servicePed.precioTotal(pedido.getId()));
			
		}
		
		return linPed;
			
	}
	
	/**
	 * Método para borrar una linea de un pedido de la BBDD mediante el id de la linea y
	 * el id del pedido que sera especificado en la ruta
	 * @param idPed
	 * @param idLinPed
	 */
	@DeleteMapping("/pedidos/{idPed}/lineas/{idLinPed}")
	public void deleteLinPed(@PathVariable int idPed, @PathVariable int idLinPed)throws Exception {

		Pedido pedido = servicePed.findById(idPed);
		LineaPedido linea = serviceLinPed.findById(idLinPed);
		
		if (pedido == null) {
			
			throw new PedidoNotFoundException(idPed);
			//En el caso de que no exista ese pedido lanzará la excepcion propia
			
		} else if (linea == null){
			
			throw new LineaPedidoNotFoundException(idLinPed);
			
		} else {
			
			serviceLinPed.delete(idLinPed);
			
		}
		
		throw new NoContentException();
		
	}
	
	/**
	 * Añade una nueva linea al pedido con el producto y la cantidad especificada
	 * @param idPed
	 * @param idProd
	 * @param cant
	 * @throws Exception
	 */
	@PostMapping("/pedidos/{idPed}/lineas")
	public Pedido addLinea(@PathVariable int idPed, @RequestBody LineaPedido linPed)throws Exception {
		
		Pedido pedido = servicePed.findById(idPed);
		
		if (pedido == null) {
			
			throw new PedidoNotFoundException(idPed);
			//En el caso de que no exista ese pedido lanzará la excepcion propia
			
		} else {
			
			servicePed.addLinPed(linPed.getProducto().getId(), pedido, linPed.getCantidad());
			pedido.setTotal(servicePed.precioTotal(pedido.getId())); 
			
		}
		
		return pedido;
		
	}
	
	@ExceptionHandler(UsuarioNotFoundException.class)
	public ResponseEntity<ApiError> UsuarioNotFoundException(UsuarioNotFoundException usuarioException) {
		ApiError apiError = new ApiError(usuarioException.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	@ExceptionHandler(LineaPedidoNotFoundException.class)
	public ResponseEntity<ApiError> LineaPedidoNotFoundException(LineaPedidoNotFoundException lineaPedidoException) {
		ApiError apiError = new ApiError(lineaPedidoException.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	@ExceptionHandler(PedidoNotFoundException.class)
	public ResponseEntity<ApiError> PedidoNotFoundException(PedidoNotFoundException pedidoException) {
		ApiError apiError = new ApiError(pedidoException.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	@ExceptionHandler(ProductoNotFoundException.class)
	public ResponseEntity<ApiError> ProductoNotFoundException(ProductoNotFoundException productoException) {
		ApiError apiError = new ApiError(productoException.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	@ExceptionHandler(NoContentException.class)
	public ResponseEntity<ApiError> NoContentException(NoContentException noContent) {
		ApiError apiError = new ApiError(noContent.getMessage());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiError);
	}
	
}
