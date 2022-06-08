package com.ejemplo.demo.exception;

public class PedidoNotFoundException extends RuntimeException{

	static final long serialVersionUID = 3L;

	public PedidoNotFoundException(int id){
		
		super("No hay ningun pedido con ID: "+id);
		
	}
	
}
