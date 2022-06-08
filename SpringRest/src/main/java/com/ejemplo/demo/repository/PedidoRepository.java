package com.ejemplo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.demo.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
