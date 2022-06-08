package com.ejemplo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.demo.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{

}
