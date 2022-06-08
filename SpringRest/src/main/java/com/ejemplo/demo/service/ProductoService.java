package com.ejemplo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplo.demo.model.Producto;
import com.ejemplo.demo.repository.ProductoRepository;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository repoProd;
	
	
	/**
	 * Busca todos los producto de la BBDD
	 * @return devuelve la lista de producto
	 */
	public List<Producto> findAll(){
		
		return repoProd.findAll();
		
	}
	
	/**
	 * Busca un producto por su ID
	 * @param id
	 * @return devuelve el producto si lo encuentra o null en caso negativo
	 */
	public Producto findById(int id) {
		
		return repoProd.findById(id).orElse(null);
		
	}
	
}
