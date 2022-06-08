package com.ejemplo.demo.exception;

public class ProductoNotFoundException extends RuntimeException{

	static final long serialVersionUID = 2L;

	public ProductoNotFoundException(int id){
		
		super("No hay ningun producto con ID: "+id);
		
	}
	
}
