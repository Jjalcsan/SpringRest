package com.ejemplo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.demo.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{

}
