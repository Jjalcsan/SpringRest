package com.ejemplo.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	private String nick;
	
	@Column(name = "contra", nullable = false)
	private String contra;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "telefono", nullable = false)
	private String telefono;
	
	@Column(name = "direccion", nullable = false)
	private String direccion;
	
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Pedido> pedidos;
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Constructores

	/**
	 * Constructor vacío
	 */
	public Usuario() {}
	
	/**
	 * Constructor con el nick del usuario
	 * @param nick
	 */
	public Usuario( String nick) {	
		this.nick = nick;	
	}

	/**
	 * Constructor con las propiedades necesarias para hacer login
	 * @param nick
	 * @param contra
	 */
	public Usuario(String nick, String contra) {		
		this.nick = nick;
		this.contra = contra;				
	}
	
	/**
	 * Constructor con todos los parametros del usuario que inicializa la lista de pedidos vacía
	 * @param nick
	 * @param contra
	 * @param nombre
	 * @param email
	 * @param direccion
	 * @param telefono
	 */
	public Usuario(String nombre,String nick, String contra, String email, String telefono, String direccion) {
		
		super();
		this.nombre = nombre;
		this.nick = nick;
		this.contra = contra;
		this.email = email;
		this.telefono = telefono;
		this.direccion = direccion;
		this.pedidos = new ArrayList<>();
		
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Setters y Getters
	
	/**
	 * Método get del nombre del usuario
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método set del nombre del usuario
	 * @return
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método get del nick del usuario
	 * @return
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Método set del nick del usuario
	 * @return
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Método get de la contraseña del usuario
	 * @return
	 */
	public String getContra() {
		return contra;
	}

	/**
	 * Método set de la contraseña del usuario
	 * @return
	 */
	public void setContra(String contra) {
		this.contra = contra;
	}

	/**
	 * Método get del email del usuario
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Método set del email del usuario
	 * @return
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Método get del teléfono del usuario
	 * @return
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Método set del teléfono del usuario
	 * @return
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Método get de la dirección del usuarios
	 * @return
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Método set de la dirección del usuarios
	 * @return
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	/**
	 * Método get de la lista de pedidos del usuario
	 * @return
	 */
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	/**
	 * Método set de la lista de pedidos del usuario
	 * @return
	 */
	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	//Metodos Override	
	
	/**
	 * Método override hashCode que genera un código Hash basado en el nick del usuario
	 */
	@Override
	public int hashCode() {
		return Objects.hash(contra, direccion, email, nick, nombre, telefono);
	}

	/**
	 * Método override equals que compara a dos usuarios a través de sus propiedades
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(contra, other.contra) && Objects.equals(direccion, other.direccion)
				&& Objects.equals(email, other.email) && Objects.equals(nick, other.nick)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(telefono, other.telefono);
	}

	/**
	 * Método override toString que formará una linea con las propiedades del usuario
	 */
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", nick=" + nick + ", contra=" + contra + ", email=" + email
				+ ", telefono=" + telefono + ", direccion=" + direccion + "]";
	}
	
}