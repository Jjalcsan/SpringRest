package com.ejemplo.demo.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Representa la entidad línea de pedido de la BBDD
 * Una línea de pedido estará identificada por un id que aumentará secuencialmente
 * Ademas tambien estará relacionada con el pedido al que pertenece y el id del producto que contiene
 * También tendra una cantidad asignada al producto que se quiere comprar
 * @author Juan Jose
 *
 */
@Entity
@Table(name = "lineaPedido")
public class LineaPedido {
	
	/**
	 * El id de la línea de pedido
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * El id del pedido al que pertenece
	 */
	@ManyToOne
	@JoinColumn(name = "pedido_id")
	@JsonBackReference
	private Pedido pedido;
	
	/**
	 * El id del producto que contiene
	 */
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name = "producto_id")
	@JsonBackReference
	private Producto producto;
	
	/**
	 * La cantidad que se quiere comprar
	 */
	@Column(name = "cantidad", nullable = false)
	private double cantidad;

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Constructores
	
	/**
	 * Constructor vacio
	 */
	public LineaPedido() {}
	
	public LineaPedido(int id) {
		
		this.id = id;
		
	}
	
	/**
	 * Constructor que recibirá un pedido y un producto
	 * @param producto
	 * @param pedido
	 */
	public LineaPedido(Producto prod, Pedido ped) {
		
		this.producto = prod;
		this.pedido = ped;
		
	}
	
	/**
	 * Constructor que recibirá todas las propiedades de la línea del pedido
	 * @param pedido
	 * @param producto
	 * @param unidades
	 */
	public LineaPedido(Producto producto, Pedido pedido, double cantidad) {

		this.pedido = pedido;
		this.producto = producto;
		this.cantidad = cantidad;
		
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Setters y Getters
	
	/**
	 * Método get del id de la la línea del pedido
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Método set del id de la la línea del pedido
	 * @return
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Método get del pedido al que pertenece la línea
	 * @return
	 */
	public Pedido getPedido() {
		return pedido;
	}

	/**
	 * Método set del pedido al que pertenece la línea
	 * @return
	 */
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	/**
	 * Método get del producto que contiene la línea
	 * @return
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * Método set del producto que contiene la línea 
	 * @return
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	/**
	 * Método get de la cantidad del producto deseado
	 * @return
	 */
	public double getCantidad() {
		return cantidad;
	}

	/**
	 * Método set de la cantidad del producto deseado 
	 * @return
	 */
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	//Metodos Override

	/**
	 * Método override hashCode que genera un código Hash basado en el id de la línea del pedido
	 */
	@Override
	public String toString() {
		return "LineaPedido [id=" + id + ", pedido=" + pedido + ", producto=" + producto + ", cantidad=" + cantidad
				+ "]";
	}

	/**
	 * Método override equals que compara a dos línea del pedido a través de su id
	 */
	@Override
	public int hashCode() {
		return Objects.hash(pedido, producto);
	}

	/**
	 * Método override toString que formará una linea con las propiedades de la línea del pedido
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineaPedido other = (LineaPedido) obj;
		return Objects.equals(pedido, other.pedido) && Objects.equals(producto, other.producto);
	}

}
