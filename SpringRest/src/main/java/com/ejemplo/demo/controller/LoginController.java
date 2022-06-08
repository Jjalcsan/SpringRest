package com.ejemplo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	@GetMapping("/usuario")
	public ResponseEntity<Usuario> findById(@RequestParam String nombre, @RequestParam String contra) throws Exception{
		
		Usuario usuario = serviceUsu.findById(nombre);
		ResponseEntity<Usuario> findbyid = ResponseEntity.notFound().build();
		
		if(usuario != null && usuario.getContra().equals(contra)) {
			
			findbyid = ResponseEntity.ok(usuario);
			
		} else {
			
			throw new UsuarioNotFoundException(nombre);
			//En el caso de que no exista ese usuario lanzará la excepcion propia
			
		}
		
		return findbyid;
		
	}
	
	
	
}
