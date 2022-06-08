package com.ejemplo.demo.exception;

public class LineaPedidoNotFoundException extends RuntimeException{

	static final long serialVersionUID = 4L;

	public LineaPedidoNotFoundException(int id){
		
		super("No hay ninguna linea en el pedido con ID: "+id);
		
	}
	
}
