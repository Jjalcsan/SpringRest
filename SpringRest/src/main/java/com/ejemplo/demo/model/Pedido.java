package com.ejemplo.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Representa la entidad pedido de la BBDD
 * Un pedido estará identificado por su id que aumentará de manera secuencial
 * Además tendrá una fecha de creación, un tipo de envio, un precio total, un usuario asignado
 * y unas lineas correspondientes a cada producto añadido
 * @author Juan Jose
 *
 */
@Entity
@Table(name = "pedido")
public class Pedido {
	
	/**
	 * El identificador del pedido que irá aumentando secuencialmente
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/**
	 * La fecha de creación del pedido
	 */
	@Column(name = "fecha", nullable = true)
	private LocalDate fecha;
	
	/**
	 * El tipo de envío del pedido
	 */
	@Column(name = "metodoEnvio", nullable = true)
	private String metodoEnvio;

	/**
	 * Las líneas del pedido
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch= FetchType.EAGER, orphanRemoval = true)
	@NotFound(action=NotFoundAction.IGNORE)
	@JsonManagedReference
	private List<LineaPedido> lineasPedido = new ArrayList<LineaPedido>();
	
	/**
	 * El precio total del pedido
	 */
	@Column(name = "total")
	private double total;
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Constructores
	
	/**
	 * Constructor vacío
	 */
	public Pedido() {
		
		super();
		this.fecha = LocalDate.now();
		
	}
	
	/**
	 * Constructor con el tipo de envio
	 * @param metodoEnvio
	 */
	public Pedido (String metodoEnvio) {
		
		super();
		this.fecha = LocalDate.now();
		this.metodoEnvio = metodoEnvio;
		
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Setters y Getters
	
	/**
	 * Método get del id del pedido
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Método set del id del pedido
	 * @return
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Método get de la fecha del pedido
	 * @return
	 */
	public LocalDate getFecha() {
		return fecha;
	}
	
	/**
	 * Método set de la fecha del pedido
	 * @return
	 */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	/**
	 * Método get del tipo de envío
	 * @return
	 */
	public String getMetodoEnvio() {
		return metodoEnvio;
	}

	/**
	 * Método set del tipo de envío
	 * @return
	 */
	public void setMetodoEnvio(String metodoEnvio) {
		this.metodoEnvio = metodoEnvio;
	}

	/**
	 * Método get de las líneas del pedido
	 * @return
	 */
	public void setLineasPedido(List<LineaPedido> lineasPedido) {
		this.lineasPedido = lineasPedido;
	}

	/**
	 * Método set de las líneas del pedido
	 * @return
	 */
	public List<LineaPedido> getLineasPedido() {
		return lineasPedido;
	}

	/**
	 * Método get del total del pedido
	 * @return
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Método set del total del pedido
	 * @return
	 */
	public void setTotal(double total) {
		this.total = total;
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	//Metodos Override
	
	/**
	 * Método override hashCode que genera un código Hash basado en el id del pedido
	 */
	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, lineasPedido, metodoEnvio);
	}

	/**
	 * Método override equals que compara a dos pedidos a través de sus propiedades
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(fecha, other.fecha) && id == other.id && Objects.equals(lineasPedido, other.lineasPedido)
				&& Objects.equals(metodoEnvio, other.metodoEnvio);
	}

}