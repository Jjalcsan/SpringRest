package com.ejemplo.demo.exception;

public class UsuarioNotFoundException extends RuntimeException{

	static final long serialVersionUID = 1L;

	public UsuarioNotFoundException(String nick){
		
		super("No hay ningun usuario con el nick: "+nick);
		
	}
	
}
