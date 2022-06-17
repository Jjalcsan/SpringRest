package com.ejemplo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.demo.exception.ApiError;
import com.ejemplo.demo.exception.UsuarioNotFoundException;
import com.ejemplo.demo.model.Usuario;
import com.ejemplo.demo.service.UsuarioService;

@RestController
public class LoginController {

	
	@Autowired
	private UsuarioService serviceUsu;
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Metodos de los usuarios
		
	/**
	 * Metodo de consulta que se realiza en el login para comprobar que existe un usuario y que su contraseña es correcta
	 * @param nombre
	 * @param contra
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/usuarios")
	public ResponseEntity<Usuario> findById(@RequestParam String nick, @RequestParam String contra) throws Exception{
		
		Usuario usuarioBBDD = serviceUsu.findById(nick);
		ResponseEntity<Usuario> findbyid = ResponseEntity.notFound().build();
		
		if(usuarioBBDD != null && usuarioBBDD.getContra().equals(contra)) {
			
			findbyid = ResponseEntity.ok(usuarioBBDD);
			
		} else {
			
			throw new UsuarioNotFoundException(nick);
			//En el caso de que no exista ese usuario lanzará la excepcion propia
			
		}
		
		return findbyid;
		
	}
	
	@ExceptionHandler(UsuarioNotFoundException.class)
	public ResponseEntity<ApiError> UsuarioException(UsuarioNotFoundException UsuarioException) {
		ApiError apiError = new ApiError(UsuarioException.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	
}
