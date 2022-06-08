package com.ejemplo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplo.demo.model.Pedido;
import com.ejemplo.demo.model.Usuario;
import com.ejemplo.demo.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repoUsu;

	/**
	 * Busca todos los usuarios de la BBDD
	 * @return devuelve la lista de usuarios
	 */
	public List<Usuario> findAll(){
		
		return repoUsu.findAll();
		
	}
	
	/**
	 * Busca un usuario por su nick
	 * @param nick
	 * @return devuelve el usuario si lo encuentra o null en caso contrario
	 */
	public Usuario findById(String nick) {
		
		return repoUsu.findById(nick).orElse(null);
		
	}
	
	/**
	 * Comprueba si existe un usuario
	 * @param nick
	 * @param contra
	 * @return devolvera true si existe o false en caso contrario
	 */
	public boolean existeUsuario(String nick, String contra) {
		
		boolean encontrado = false;
		Usuario usuario = findById(nick);
		
		if ( usuario != null && usuario.getNick().equals(nick) && usuario.getContra().equals(contra)) {
			
			encontrado = true;
			
		}
		
		return encontrado;
		
	}
	
	/**
	 * Busca la lista de pedidos asociados con el usuario
	 * @param usuario
	 * @return devuelve la lista de pedidos
	 */
	public List<Pedido> pedidosUsuario(Usuario usuario){
		
		return usuario.getPedidos();
		
	}
}
